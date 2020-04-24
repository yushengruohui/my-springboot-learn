package com.ys.shiro.config.shiro3;

import com.ys.shiro.domain.entity.Permission;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 自定义 JwtAuthenticatedFilter，支持restful风格，处理jwt验证
 *
 * @author yusheng
 * Created on 2020-04-19 19:02
 **/
public class JwtAuthenticatedFilter extends BasicHttpAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticatedFilter.class);

    /**
     * 是否允许当前请求访问
     *
     * @param servletRequest  请求
     * @param servletResponse 响应
     * @param mappedValue     请求方法集合
     * @return true:继续执行过滤器链，没有下一个匹配的过滤器，则允许访问资源||false: 结束过滤器链执行，进入onAccessDenied
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) {
        //获取 Token
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Cookie cookie = WebUtils.getCookie(request, JwtTokenUtils.TOKEN_KEY);
        String token = cookie != null ? cookie.getValue() : null;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (token != null && !token.trim().equals("")) {
            String loginIp = JwtTokenUtils.getIpAddr(request);
            Subject subject = getSubject(servletRequest, servletResponse);
            try {
                subject.login(new MyJwtToken(token, loginIp));
                List<Permission> permissions = JwtTokenUtils.getPermissionByClaims(JwtTokenUtils.getClaimsFromToken(token));
                String requestURI = request.getRequestURI();
                String requestMethod = request.getMethod();
                for (Permission permission : permissions) {
                    if (matchers(permission.getAccessibleUrl(), requestURI) && (permission.getAccessibleMethod().toUpperCase().equals("ALL") || permission.getAccessibleMethod().toUpperCase().equals(requestMethod))) {
                        // 允许访问
                        return true;
                    }
                }
                return false;
            } catch (Exception e) {
                //登陆失败处理
                if (e instanceof UnknownAccountException) {
                    //空账号
                    response.setStatus(401);
                    return false;
                } else if (e instanceof IncorrectCredentialsException) {
                    // 异地登陆
                    response.setStatus(402);
                    JwtTokenUtils.cleanToken(response);
                    return false;
                } else if (e instanceof ExpiredCredentialsException) {
                    // 过期凭证
                    response.setStatus(402);
                    JwtTokenUtils.cleanToken(response);
                    return false;
                } else {
                    // 未知错误
                    response.setStatus(402);
                    JwtTokenUtils.cleanToken(response);
                    e.printStackTrace();
                    return false;
                }
            }
        }
        // 未登录处理
        log.error("未登录");
        response.setStatus(401);
        return false;
    }

    /**
     * 访问决策，请求的最终处理
     *
     * @param servletRequest  请求
     * @param servletResponse 响应
     * @return true: 允许访问资源||false: 禁止访问，结束过滤器链执行
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) {
        Subject subject = getSubject(servletRequest, servletResponse);
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 未认证的情况上面已经处理  这里处理未授权
        if (subject != null && subject.isAuthenticated()) {
            //  已经认证但未授权的情况处理
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setStatus(403);
            // 告知客户端JWT没有权限访问此资源
        }
        // 过滤链终止
        return false;
    }

    private boolean matchers(String permissionUrl, String requestUrl) {
        PatternMatcher matcher = new AntPathMatcher();
        return matcher.matches(permissionUrl, requestUrl);
    }
}
