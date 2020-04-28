package com.ys.security.config.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义未登录时：返回状态码401
 * 未登录时访问了需要权限验证的资源URL处理
 *
 * @author yusheng
 */
@Component("urlAuthenticationEntryPoint")
public class UrlAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UrlAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {
        httpServletResponse.setContentType("application/json;");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setStatus(401);
        try {
            httpServletResponse.getWriter().write("please login");
        } catch (IOException ioException) {
            log.error("没有访问权限响应出错");
            ioException.printStackTrace();
        }
    }
}
