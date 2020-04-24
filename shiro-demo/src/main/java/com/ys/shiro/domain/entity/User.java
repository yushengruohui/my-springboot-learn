package com.ys.shiro.domain.entity;

import java.io.Serializable;

/**
 * (User)表实体类
 *
 * @author yusheng
 * @create-time 2020-04-17 23:43:12
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private String account;

    private String username;

    private String password;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(300);
        sb.append("{");
        sb.append(" ").append("userId").append(" : ");
        sb.append(userId).append(" ,");
        sb.append(" ").append("account").append(" : ");
        sb.append("\"").append(account).append("\" ,");
        sb.append(" ").append("username").append(" : ");
        sb.append("\"").append(username).append("\" ,");
        sb.append(" ").append("password").append(" : ");
        sb.append("\"").append(password).append("\" ,");
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("}");
        return sb.toString();
    }
}