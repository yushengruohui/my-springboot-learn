package com.ys.shiro.service.impl;

import com.ys.shiro.domain.entity.Permission;
import com.ys.shiro.dao.PermissionDao;
import com.ys.shiro.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Permission表业务层具体实现类
 * 
 * @create-time 2020-04-17 23:41:04
 * @author yusheng
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
    
    @Resource
    private PermissionDao permissionDao;

    /**
     * 获取 Permission 表所有记录
     *
     * @return permissionPOList
     */
    @Override
    public List<Permission> listAllPermission() {
        return permissionDao.listAllPermission();
    }
    
    /**
     * 通过主键获取一条 Permission 表记录
     *
     * @param permissionId Permission表主键
     * @return permissionPO  
     */
    @Override
    public Permission getPermissionById(Long permissionId) {
        return permissionDao.getPermissionById(permissionId);
    }
    
    /**
     * 获取一条符合要求的 Permission 表记录
     *
     * @param permissionQO 查询条件对象
     * @return permissionPO  
     */
    @Override
    public Permission getPermission(Permission permissionQO) {
        return permissionDao.getPermission(permissionQO);
    }
    
    /**
     * 获取 Permission 表中符合查询条件的所有记录
     *
     * @param permissionQO 查询条件对象
     * @return PermissionPOList
     */
    @Override
    public List<Permission> listPermission(Permission permissionQO) {
        return permissionDao.listPermission(permissionQO);
    }
    
    /**
     * 在 Permission 表中添加一条记录
     *
     * @param permissionQO 查询条件对象
     * @return true|false
     */
    @Override
    public Boolean insertPermission(Permission permissionQO) {
        return  1 == permissionDao.insertPermission(permissionQO);
    }

    /**
     * 在 Permission 表中修改一条记录
     *
     * @param permissionQO 主键不为空的查询条件对象
     * @return true|false
     */
    @Override
    public Boolean updatePermission(Permission permissionQO) {
        return 1 == permissionDao.updatePermission(permissionQO);
    }

    /**
     * 通过主键删除 Permission 表中的一条记录
     *
     * @param permissionId Permission表主键
     * @return true|false
     */
    @Override
    public Boolean deletePermissionById(Long permissionId) {
        return 1 == permissionDao.deletePermissionById(permissionId);
    }
    
    /**
     * 查询 Permission 表中是否存在符合查询条件的记录
     *
     * @param permissionQO 查询条件对象
     * @return true|false
     */
    @Override
    public Boolean existPermission(Permission permissionQO) {
        return null != permissionDao.existPermission(permissionQO);
    }
    
    /**
     * 统计 Permission 表中符合查询条件的记录条数
     *
     * @param permissionQO 查询条件对象
     * @return 统计值
     */
    @Override
    public Integer countPermission(Permission permissionQO) {
        return permissionDao.countPermission(permissionQO);
    }
    
    /**
     * 保存一条 Permission 表记录
     *
     * @param permissionQO 查询条件对象
     * @return true|false
     */
    @Override
    public Boolean savePermission(Permission permissionQO) {
        Long permissionId = permissionQO.getPermissionId( );
        if (permissionId != null) {
            return 1 == permissionDao.updatePermission(permissionQO);
        } else {
            return 1 == permissionDao.insertPermission(permissionQO);
        }
    }
}