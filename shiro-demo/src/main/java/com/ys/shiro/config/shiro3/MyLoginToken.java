package com.ys.shiro.config.shiro3;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 正常登陆用的token
 *
 * @author yusheng
 */
public class MyLoginToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;

    private String account;

    private String password;

    // 可以自行添加想要的属性


    public MyLoginToken() {
    }

    public MyLoginToken(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Object getPrincipal() {
        return this.account;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }

}
