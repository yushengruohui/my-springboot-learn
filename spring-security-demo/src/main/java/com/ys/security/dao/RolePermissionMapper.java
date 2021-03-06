package com.ys.security.dao;

import com.ys.security.domain.entity.RolePermission;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * RolePermission表数据库访问层接口
 * 
 * @create-time 2020-04-17 15:51:25
 * @author yusheng
 */
public interface RolePermissionMapper {

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
     * @return 1|0
     */
    Integer insertRolePermission(RolePermission rolePermissionQO);

    /**
     * 在 RolePermission 表中修改一条记录
     *
     * @param rolePermissionQO 主键不为空的查询条件对象
     * @return 1|0
     */
    Integer updateRolePermission(RolePermission rolePermissionQO);

    /**
     * 通过主键删除 RolePermission 表中的一条记录
     *
     * @param rolePermissionId RolePermission表主键
     * @return 1|0
     */
    Integer deleteRolePermissionById(Long rolePermissionId);

    /**
     * 查询 RolePermission 表是否存在符合查询条件的一条记录
     *
     * @param rolePermissionQO 查询条件对象
     * @return 1|null
     */
    Integer existRolePermission(RolePermission rolePermissionQO);
    
    /**
     * 统计 RolePermission 表中符合查询条件的记录条数
     *
     * @param rolePermissionQO 查询条件对象
     * @return 统计值
     */
    Integer countRolePermission(RolePermission rolePermissionQO);
    
    
}