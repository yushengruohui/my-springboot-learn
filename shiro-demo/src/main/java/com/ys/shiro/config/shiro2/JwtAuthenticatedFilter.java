package com.ys.shiro.config.shiro2;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created on 2020-04-19 19:02
 *
 * @author yusheng
 **/
public class JwtAuthenticatedFilter extends BasicHttpAuthenticationFilter {

    /**
     * 是否允许当前访问
     * 判断当前请求是否需要身份验证
     *
     * @param servletRequest
     * @param servletResponse
     * @param mappedValue
     * @return true:继续执行过滤器链||false: 结束过滤器链执行，进入onAccessDenied
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) {
        //判断请求的请求头是否带上 Token
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Cookie cookie = WebUtils.getCookie(request, JwtTokenUtils.TOKEN_KEY);
        String token = cookie != null ? cookie.getValue() : null;
        // token 检测
        if (token != null && !token.trim().equals("")) {
            // 检测用户登陆IP
            final String audienceInToken = JwtTokenUtils.getClaimsFromToken(token).getAudience();
            String loginIp = JwtTokenUtils.getIpAddr(request);
            if (audienceInToken == null || audienceInToken.trim().equals("") || loginIp == null || loginIp.trim().equals("") || !loginIp.equals(audienceInToken)) {
                // ip地址不一致，拒接访问
                response.setStatus(401);
                return false;
            }
            if (!JwtTokenUtils.isTokenExpired(token)) {
                // token 正常，登陆
                Subject subject = getSubject(servletRequest, servletResponse);
                subject.login(new MyJwtToken(token, token, token));
                return true;
            }
            // 无效token 处理
            response.setStatus(402);
            JwtTokenUtils.cleanToken(response);
            return false;
        }
        // 未登录处理
        response.setStatus(401);
        return false;
    }

    /**
     * 访问决策，请求的最终处理
     *
     * @param servletRequest
     * @param servletResponse
     * @return true: 允许访问资源||false: 禁止访问
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) {
        Subject subject = getSubject(servletRequest, servletResponse);
        // 未认证的情况上面已经处理  这里处理未授权
        if (subject != null && subject.isAuthenticated()) {
            //  已经认证但未授权的情况处理
            System.out.println("没有权限");
            // 告知客户端JWT没有权限访问此资源
        }
        // 过滤链终止
        return false;
    }
}
