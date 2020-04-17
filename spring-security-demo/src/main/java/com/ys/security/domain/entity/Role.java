package com.ys.security.domain.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * (Role)表实体类
 * 
 * @create-time 2020-04-17 15:27:39
 * @author yusheng
 */
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long roleId;

    private String roleName;

    
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        StringBuffer sb=new StringBuffer(300);
        sb.append("{");
        sb.append(" ").append("roleId").append(" : ");
        sb.append(roleId).append(" ,");
        sb.append(" ").append("roleName").append(" : ");
        sb.append("\"").append(roleName).append("\" ,");
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("}");
        return sb.toString();
    }
}