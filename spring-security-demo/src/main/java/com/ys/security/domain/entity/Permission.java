package com.ys.security.domain.entity;

import java.io.Serializable;

/**
 * (Permission)表实体类
 *
 * @author yusheng
 * @create-time 2020-04-17 15:27:39
 */
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long permissionId;

    private String description;

    private String accessibleMethod;

    private String accessibleUrl;

    public Permission() {

    }

    public Permission(Long permissionId, String description, String accessibleMethod, String accessibleUrl) {
        this.permissionId = permissionId;
        this.description = description;
        this.accessibleMethod = accessibleMethod;
        this.accessibleUrl = accessibleUrl;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccessibleMethod() {
        return accessibleMethod;
    }

    public void setAccessibleMethod(String accessibleMethod) {
        this.accessibleMethod = accessibleMethod;
    }

    public String getAccessibleUrl() {
        return accessibleUrl;
    }

    public void setAccessibleUrl(String accessibleUrl) {
        this.accessibleUrl = accessibleUrl;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(300);
        sb.append("{");
        sb.append(" ").append("permissionId").append(" : ");
        sb.append(permissionId).append(" ,");
        sb.append(" ").append("description").append(" : ");
        sb.append("\"").append(description).append("\" ,");
        sb.append(" ").append("accessibleMethod").append(" : ");
        sb.append("\"").append(accessibleMethod).append("\" ,");
        sb.append(" ").append("accessibleUrl").append(" : ");
        sb.append("\"").append(accessibleUrl).append("\" ,");
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("}");
        return sb.toString();
    }
}