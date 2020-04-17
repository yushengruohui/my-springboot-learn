package com.ys.security.config.security;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * Created on 2020-04-14 11:23
 *
 * @author yusheng
 **/
public class MyGrantedAuthority implements Serializable, GrantedAuthority {

    private static final long serialVersionUID = 1L;

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public MyGrantedAuthority() {
        // 要有无参构造函数，否则使用objectMapper，map转对象时会出错。
    }

    public MyGrantedAuthority(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public String toString() {
        return "{"
                + "\"role\":\""
                + role + '\"'
                + '}';
    }
}
