package com.ys.security.config.security;

import org.springframework.security.access.ConfigAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * 通行配置类
 * 可以在这里配置能通过的角色
 */
public class MyConfigAttribute implements ConfigAttribute {

    private final HttpServletRequest httpServletRequest;

    public MyConfigAttribute(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }


    @Override
    public String getAttribute() {
        return null;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }
}