package com.ys.security.config.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 自定义登录认证过程
 */
@Component("selfAuthenticationProvider")
public class SelfAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private SelfUserDetailsService selfUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //表单输入的账号
        String account = (String) authentication.getPrincipal();
        //表单输入的密码
        String password = (String) authentication.getCredentials();
        // 登陆的额外信息，如验证码，现在不处理验证码。
        // MyWebAuthenticationDetails validatedInfo = (MyWebAuthenticationDetails) authentication.getDetails();
        // String validatedCode = validatedInfo.getValidatedCode();
        // String macAddress = validatedInfo.getMacAddress();
        // String remoteAddress = validatedInfo.getRemoteAddress();
        // 获取认证信息
        MyUserDetails userInfo = (MyUserDetails) selfUserDetailsService.loadUserByUsername(account);
        //校验账号和密码
        boolean matches = new BCryptPasswordEncoder().matches(password, userInfo.getPassword());
        if (!matches) {
            throw new BadCredentialsException("The password is incorrect!!");
        }
        // 生成用户登陆后的认证信息
        return new UsernamePasswordAuthenticationToken(userInfo, userInfo.getPassword(), userInfo.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
