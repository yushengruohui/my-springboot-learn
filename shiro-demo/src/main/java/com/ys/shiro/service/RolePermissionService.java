package com.ys.shiro.service;

import com.ys.shiro.domain.entity.RolePermission;
import java.util.List;

/**
 * RolePermission表业务层具体实现类
 * 
 * @create-time 2020-04-17 23:41:18
 * @author yusheng
 */
public interface RolePermissionService {

    /**
     * 获取 RolePermission 表所有记录
     *
     * @return rolePermissionPOList
     */
    List<RolePermission> listAllRolePermission();
    
    /**
     * 通过主键获取一条 RolePermission 表记录
     *
     * @param rolePermissionId RolePermission表主键
     * @return rolePermissionPO  
     */
    RolePermission getRolePermissionById(Long rolePermissionId);
    
    /**
     * 获取一条符合要求的 RolePermission 表记录
     *
     * @param rolePermissionQO 查询条件对象
     * @return rolePermissionPO  
     */
    RolePermission getRolePermission(RolePermission rolePermissionQO);
    
    /**
     * 获取 RolePermission 表中符合查询条件的所有记录
     *
     * @param rolePermissionQO 查询条件对象
     * @return RolePermissionPOList
     */
    List<RolePermission> listRolePermission(RolePermission rolePermissionQO);

    /**
     * 在 RolePermission 表中添加一条记录
     *
     * @param rolePermissionQO 查询条件对象
     * @return true|false
     */
    Boolean insertRolePermission(RolePermission rolePermissionQO);

    /**
     * 在 RolePermission 表中修改一条记录
     *
     * @param rolePermissionQO 主键不为空的查询条件对象
     * @return true|false
     */
    Boolean updateRolePermission(RolePermission rolePermissionQO);

    /**
     * 通过主键删除 RolePermission 表中的一条记录
     *
     * @param rolePermissionId RolePermission表主键
     * @return true|false
     */
    Boolean deleteRolePermissionById(Long rolePermissionId);

    /**
     * 查询 RolePermission 表中是否存在符合查询条件的记录
     *
     * @param rolePermissionQO 查询条件对象
     * @return true|false
     */
    Boolean existRolePermission(RolePermission rolePermissionQO);
    
    /**
     * 统计 RolePermission 表中符合查询条件的记录条数
     *
     * @param rolePermissionQO 查询条件对象
     * @return 统计值
     */
    Integer countRolePermission(RolePermission rolePermissionQO);
    
    /**
     * 保存一条 RolePermission 表记录
     *
     * @param rolePermissionQO 查询条件对象
     * @return true|false
     */
    Boolean saveRolePermission(RolePermission rolePermissionQO);
}