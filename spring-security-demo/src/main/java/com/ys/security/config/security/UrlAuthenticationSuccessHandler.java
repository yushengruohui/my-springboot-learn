package com.ys.security.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 自定义登录验证成功后处理，并生成token，响应状态码200及token
 */
@Component("urlAuthenticationSuccessHandler")
public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        // 获取认证信息
        SelfUserDetails userInfo = (SelfUserDetails) authentication.getPrincipal();
        // 表单输入的账号
        String account = userInfo.getAccount();

        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        List<String> roleList = new ArrayList<>();
        if (grantedAuthorities != null) {
            // 角色名自动大写
            roleList = grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        }

        // 生成token
        String token = JwtTokenUtils.selfGenerateToken(account, userInfo.getUserId(), userInfo.getUsername(), roleList, userInfo.getPermissions());

        // 设计 token 响应头
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setStatus(200);
        SetSelfCookie2(httpServletResponse, token, JwtTokenUtils.EXPIRATION);
        // 让前端解析该 token ，获取 token 中的常用信息
        Map<String, String> responseInfo = new HashMap<>();
        httpServletResponse.getWriter().write(token);
    }

    /**
     * IE 6、J2EE 5、 tomcat 7.0 以前的版本的设置cookie httpOnly 的方式
     *
     * @param response
     * @param cookieValue
     * @param cookieMaxAge
     */
    private void SetSelfCookie(HttpServletResponse response, String cookieValue, long cookieMaxAge) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(JwtTokenUtils.TOKEN_KEY).append("=").append(cookieValue).append(";");
        if (cookieMaxAge == 0) {
            buffer.append("Expires=Thu Jan 01 08:00:00 CST 1970;");
        } else if (cookieMaxAge > 0) {
            buffer.append("Max-Age=").append(cookieMaxAge).append(";");
        }
        // domain:cookie所在的域名。设置域名后、前端访问该域名的资源时，自动带上该cookie，默认为服务器的域名
        // buffer.append("domain=").append("localhost").append(";");
        // 指定访问路径前缀
        buffer.append("path=").append("/").append(";");
        // 加密传输
        // buffer.append("secure;");
        buffer.append("HTTPOnly;");
        response.addHeader("Set-Cookie", buffer.toString());
    }

    /**
     * 浏览器IE 6、J2EE 5、 tomcat 7.0 之后的版本的设置cookie httpOnly 的方式
     *
     * @param response
     * @param cookieValue
     * @param cookieMaxAge
     */
    private void SetSelfCookie2(HttpServletResponse response, String cookieValue, int cookieMaxAge) {
        Cookie cookie = new Cookie(JwtTokenUtils.TOKEN_KEY, cookieValue);
        cookie.setHttpOnly(true);
        // cookie.setDomain("127.0.0.1");
        cookie.setPath("/");
        cookie.setMaxAge(cookieMaxAge);
        response.addCookie(cookie);
    }

}
