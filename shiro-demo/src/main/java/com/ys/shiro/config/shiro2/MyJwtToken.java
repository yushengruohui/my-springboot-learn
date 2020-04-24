package com.ys.shiro.config.shiro2;

import org.apache.shiro.authc.AuthenticationToken;

import java.io.Serializable;

/**
 * Created on 2020-04-19 20:23
 *
 * @author yusheng
 **/
public class MyJwtToken implements Serializable, AuthenticationToken {

    private static final long serialVersionUID = 1L;
    private String account;
    private String password;
    private String token;


    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public MyJwtToken() {
    }

    public MyJwtToken(String account, String password, String token) {
        this.token = token;
        this.password = password;
        this.account = account;
    }

    @Override
    public Object getPrincipal() {
        return account;
    }


    @Override
    public Object getCredentials() {
        return password;
    }


}
