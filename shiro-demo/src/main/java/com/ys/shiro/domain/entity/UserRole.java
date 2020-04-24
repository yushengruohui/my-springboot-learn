package com.ys.shiro.domain.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * (UserRole)表实体类
 * 
 * @create-time 2020-04-17 23:43:31
 * @author yusheng
 */
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long userRoleId;
    
    private Long userId;
    
    private Long roleId;

        
    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }
        
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
        
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        StringBuffer sb=new StringBuffer(300);
        sb.append("{");
        sb.append(" ").append("userRoleId").append(" : ");
        sb.append(userRoleId).append(" ,");
        sb.append(" ").append("userId").append(" : ");
        sb.append(userId).append(" ,");
        sb.append(" ").append("roleId").append(" : ");
        sb.append(roleId).append(" ,");
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("}");
        return sb.toString();
    }
}