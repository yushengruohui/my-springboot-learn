package com.ys.security.config.security;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 动态获取url权限配置类
 * 把能通过该url的权限存进Collection<ConfigAttribute>
 * 给访问决策器调用
 */
@Component("selfFilterInvocationSecurityMetadataSource")
public class SelfFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        // 用户的请求 request
        final HttpServletRequest request = ((FilterInvocation) o).getRequest();

        // 不设置能通行的角色，不在这里获取，否则每次请求都要访问数据库。
        Set<ConfigAttribute> attributes = new HashSet<>();

        // 通过所需权限
        ConfigAttribute configAttribute = new MyConfigAttribute(request);

        //当前为空，即拦截所有请求，让 AccessDecisionManager 进行访问抉择
        attributes.add(configAttribute);
        return attributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
