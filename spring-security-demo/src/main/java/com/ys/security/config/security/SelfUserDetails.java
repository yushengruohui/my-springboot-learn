package com.ys.security.config.security;

import com.ys.security.domain.entity.Permission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 自定义UserDetails
 *
 * @author yusheng
 */
public class SelfUserDetails implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String username;
    private String Account;
    private String password;
    private List<? extends GrantedAuthority> roleNameList;
    private List<Permission> permissions;

    public SelfUserDetails() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<? extends GrantedAuthority> getRoleNameList() {
        return roleNameList;
    }

    public void setRoleNameList(List<? extends GrantedAuthority> roleNameList) {
        this.roleNameList = roleNameList;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleNameList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
