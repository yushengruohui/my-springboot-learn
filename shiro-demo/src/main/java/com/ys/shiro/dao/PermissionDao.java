package com.ys.shiro.dao;

import com.ys.shiro.domain.entity.Permission;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Permission表数据库数据访问层接口
 * 
 * @create-time 2020-04-17 23:41:04
 * @author yusheng
 */
public interface PermissionDao {

    /**
     * 获取 Permission 表所有记录
     *
     * @return permissionPOList
     */
    List<Permission> listAllPermission();
    
    /**
     * 通过主键获取一条 Permission 表记录
     *
     * @param permissionId Permission表主键
     * @return permissionPO  
     */
    Permission getPermissionById(Long permissionId);
    
   /**
     * 获取一条符合要求的 Permission 表记录
     *
     * @param permissionQO 查询条件对象
     * @return permissionPO  
     */
    Permission getPermission(Permission permissionQO);
    
    /**
     * 获取 Permission 表中符合查询条件的所有记录
     *
     * @param permissionQO 查询条件对象
     * @return PermissionPOList
     */
    List<Permission> listPermission(Permission permissionQO);
    
    /**
     * 在 Permission 表中添加一条记录
     *
     * @param permissionQO 查询条件对象
     * @return 1|0
     */
    Integer insertPermission(Permission permissionQO);

    /**
     * 在 Permission 表中修改一条记录
     *
     * @param permissionQO 主键不为空的查询条件对象
     * @return 1|0
     */
    Integer updatePermission(Permission permissionQO);

    /**
     * 通过主键删除 Permission 表中的一条记录
     *
     * @param permissionId Permission表主键
     * @return 1|0
     */
    Integer deletePermissionById(Long permissionId);

    /**
     * 查询 Permission 表是否存在符合查询条件的一条记录
     *
     * @param permissionQO 查询条件对象
     * @return 1|null
     */
    Integer existPermission(Permission permissionQO);
    
    /**
     * 统计 Permission 表中符合查询条件的记录条数
     *
     * @param permissionQO 查询条件对象
     * @return 统计值
     */
    Integer countPermission(Permission permissionQO);
}