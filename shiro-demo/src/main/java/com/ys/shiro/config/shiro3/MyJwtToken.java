package com.ys.shiro.config.shiro3;

import org.apache.shiro.authc.AuthenticationToken;

import java.io.Serializable;

/**
 * JWTToken
 * Created on 2020-04-19 20:23
 *
 * @author yusheng
 **/
public class MyJwtToken implements Serializable, AuthenticationToken {

    private static final long serialVersionUID = 1L;

    private String account;

    private String ipAddr;

    private String token;

    public void setAccount(String account) {
        this.account = account;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public MyJwtToken() {
    }

    public MyJwtToken(String token, String ipAddr) {
        this.token = token;
        this.ipAddr = ipAddr;
        if (token != null) {
            this.account = JwtTokenUtils.getAccountFromToken(token);
        } else {
            this.account = null;
        }
    }

    @Override
    public Object getPrincipal() {
        return account;
    }


    @Override
    public Object getCredentials() {
        return token;
    }


}
