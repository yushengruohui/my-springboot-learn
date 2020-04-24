package com.ys.shiro.domain.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * (RolePermission)表实体类
 * 
 * @create-time 2020-04-17 23:41:18
 * @author yusheng
 */
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long rolePermissionId;
    
    private Long roleId;
    
    private Long permissionId;

        
    public Long getRolePermissionId() {
        return rolePermissionId;
    }

    public void setRolePermissionId(Long rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
    }
        
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
        
    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        StringBuffer sb=new StringBuffer(300);
        sb.append("{");
        sb.append(" ").append("rolePermissionId").append(" : ");
        sb.append(rolePermissionId).append(" ,");
        sb.append(" ").append("roleId").append(" : ");
        sb.append(roleId).append(" ,");
        sb.append(" ").append("permissionId").append(" : ");
        sb.append(permissionId).append(" ,");
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("}");
        return sb.toString();
    }
}