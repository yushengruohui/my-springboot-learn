package com.ys.shiro.service.impl;

import com.ys.shiro.dao.RolePermissionDao;
import com.ys.shiro.domain.entity.RolePermission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * RolePermission表业务层具体实现类
 *
 * @author yusheng
 * @create-time 2020-04-17 23:41:18
 */
@Service("rolePermissionService")
public class RolePermissionServiceImpl implements com.ys.shiro.service.RolePermissionService {

    @Resource
    private RolePermissionDao rolePermissionDao;

    /**
     * 获取 RolePermission 表所有记录
     *
     * @return rolePermissionPOList
     */
    @Override
    public List<RolePermission> listAllRolePermission() {
        return rolePermissionDao.listAllRolePermission();
    }

    /**
     * 通过主键获取一条 RolePermission 表记录
     *
     * @param rolePermissionId RolePermission表主键
     * @return rolePermissionPO
     */
    @Override
    public RolePermission getRolePermissionById(Long rolePermissionId) {
        return rolePermissionDao.getRolePermissionById(rolePermissionId);
    }

    /**
     * 获取一条符合要求的 RolePermission 表记录
     *
     * @param rolePermissionQO 查询条件对象
     * @return rolePermissionPO
     */
    @Override
    public RolePermission getRolePermission(RolePermission rolePermissionQO) {
        return rolePermissionDao.getRolePermission(rolePermissionQO);
    }

    /**
     * 获取 RolePermission 表中符合查询条件的所有记录
     *
     * @param rolePermissionQO 查询条件对象
     * @return RolePermissionPOList
     */
    @Override
    public List<RolePermission> listRolePermission(RolePermission rolePermissionQO) {
        return rolePermissionDao.listRolePermission(rolePermissionQO);
    }

    /**
     * 在 RolePermission 表中添加一条记录
     *
     * @param rolePermissionQO 查询条件对象
     * @return true|false
     */
    @Override
    public Boolean insertRolePermission(RolePermission rolePermissionQO) {
        return 1 == rolePermissionDao.insertRolePermission(rolePermissionQO);
    }

    /**
     * 在 RolePermission 表中修改一条记录
     *
     * @param rolePermissionQO 主键不为空的查询条件对象
     * @return true|false
     */
    @Override
    public Boolean updateRolePermission(RolePermission rolePermissionQO) {
        return 1 == rolePermissionDao.updateRolePermission(rolePermissionQO);
    }

    /**
     * 通过主键删除 RolePermission 表中的一条记录
     *
     * @param rolePermissionId RolePermission表主键
     * @return true|false
     */
    @Override
    public Boolean deleteRolePermissionById(Long rolePermissionId) {
        return 1 == rolePermissionDao.deleteRolePermissionById(rolePermissionId);
    }

    /**
     * 查询 RolePermission 表中是否存在符合查询条件的记录
     *
     * @param rolePermissionQO 查询条件对象
     * @return true|false
     */
    @Override
    public Boolean existRolePermission(RolePermission rolePermissionQO) {
        return null != rolePermissionDao.existRolePermission(rolePermissionQO);
    }

    /**
     * 统计 RolePermission 表中符合查询条件的记录条数
     *
     * @param rolePermissionQO 查询条件对象
     * @return 统计值
     */
    @Override
    public Integer countRolePermission(RolePermission rolePermissionQO) {
        return rolePermissionDao.countRolePermission(rolePermissionQO);
    }

    /**
     * 保存一条 RolePermission 表记录
     *
     * @param rolePermissionQO 查询条件对象
     * @return true|false
     */
    @Override
    public Boolean saveRolePermission(RolePermission rolePermissionQO) {
        Long rolePermissionId = rolePermissionQO.getRolePermissionId();
        if (rolePermissionId != null) {
            return 1 == rolePermissionDao.updateRolePermission(rolePermissionQO);
        } else {
            return 1 == rolePermissionDao.insertRolePermission(rolePermissionQO);
        }
    }
}