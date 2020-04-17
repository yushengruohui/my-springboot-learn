package com.ys.security.config.security;

import com.ys.security.domain.entity.Permission;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * 自定义权限判断管理器
 */
@Component("selfAccessDecisionManager")
public class SelfAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
        if (matchers("/auth/**", request)) {
            // 不用判断访问权限，未登录都能访问。
            return;
        } else {
            if ("anonymousUser".equals(authentication.getPrincipal().toString())) {
                // 未登录没有权限访问当前请求url
                throw new AccessDeniedException("没有访问权限!!");
            }
            // 当前用户所具有的权限
            SelfUserDetails userInfo = (SelfUserDetails) authentication.getPrincipal();
            List<Permission> permissions = userInfo.getPermissions();
            // 登陆后，验证用户访问权限
            for (Permission permission : permissions) {
                // 用户能访问的url
                String accessibleUrl = permission.getAccessibleUrl();
                // 用户拥有的请求方式
                String accessibleRequestMethod = permission.getAccessibleMethod();
                if (matchers(accessibleUrl, request) && ("ALL".equals(accessibleRequestMethod.toUpperCase()) || accessibleRequestMethod.toUpperCase().equals(request.getMethod()))) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("没有访问权限!!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    private boolean matchers(String url, HttpServletRequest request) {
        AntPathRequestMatcher matcher = new AntPathRequestMatcher(url);
        return matcher.matches(request);
    }
}
