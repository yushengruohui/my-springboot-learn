package com.ys.security.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义登录验证成功后处理，并生成token，响应状态码200及token
 */
@Component("urlAuthenticationSuccessHandler")
public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        // 获取认证信息
        MyUserDetails userInfo = (MyUserDetails) authentication.getPrincipal();
        // 表单输入的账号
        String account = userInfo.getAccount();

        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        List<String> roleList = new ArrayList<>();
        if (grantedAuthorities != null) {
            // 角色名自动大写
            roleList = grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        }
        String ipAddr = JwtTokenUtils.getIpAddr(httpServletRequest);
        // 生成token
        String token = JwtTokenUtils.selfGenerateToken(account, ipAddr, userInfo.getUserId(), userInfo.getUsername(), roleList, userInfo.getPermissions());

        // 设计 token 响应头
        JwtTokenUtils.responseToken(httpServletResponse, token, JwtTokenUtils.EXPIRATION);
    }

}
