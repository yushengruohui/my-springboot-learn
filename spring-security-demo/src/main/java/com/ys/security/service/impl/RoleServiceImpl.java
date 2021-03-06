package com.ys.security.service.impl;

import com.ys.security.dao.PermissionMapper;
import com.ys.security.dao.RoleMapper;
import com.ys.security.dao.RolePermissionMapper;
import com.ys.security.domain.entity.Permission;
import com.ys.security.domain.entity.Role;
import com.ys.security.domain.entity.RolePermission;
import com.ys.security.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Role表业务层具体实现类
 *
 * @author yusheng
 * @create-time 2020-04-17 15:27:39
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RolePermissionMapper rolePermissionMapper;
    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 获取 Role 表所有记录
     *
     * @return rolePOList
     */
    @Override
    public List<Role> listAllRole() {
        return roleMapper.listAllRole();
    }

    /**
     * 通过主键获取一条 Role 表记录
     *
     * @param roleId Role表主键
     * @return rolePO
     */
    @Override
    public Role getRoleById(Long roleId) {
        return roleMapper.getRoleById(roleId);
    }

    /**
     * 获取一条符合要求的 Role 表记录
     *
     * @param roleQO 查询条件对象
     * @return rolePO
     */
    @Override
    public Role getRole(Role roleQO) {
        return roleMapper.getRole(roleQO);
    }

    /**
     * 获取 Role 表中符合查询条件的所有记录
     *
     * @param roleQO 查询条件对象
     * @return RolePOList
     */
    @Override
    public List<Role> listRole(Role roleQO) {
        return roleMapper.listRole(roleQO);
    }

    /**
     * 在 Role 表中添加一条记录
     *
     * @param roleQO 查询条件对象
     * @return true|false
     */
    @Override
    public Boolean insertRole(Role roleQO) {
        return 1 == roleMapper.insertRole(roleQO);
    }

    /**
     * 在 Role 表中修改一条记录
     *
     * @param roleQO 主键不为空的查询条件对象
     * @return true|false
     */
    @Override
    public Boolean updateRole(Role roleQO) {
        return 1 == roleMapper.updateRole(roleQO);
    }

    /**
     * 通过主键删除 Role 表中的一条记录
     *
     * @param roleId Role表主键
     * @return true|false
     */
    @Override
    public Boolean deleteRoleById(Long roleId) {
        return 1 == roleMapper.deleteRoleById(roleId);
    }

    /**
     * 查询 Role 表中是否存在符合查询条件的记录
     *
     * @param roleQO 查询条件对象
     * @return true|false
     */
    @Override
    public Boolean existRole(Role roleQO) {
        return null != roleMapper.existRole(roleQO);
    }

    /**
     * 统计 Role 表中符合查询条件的记录条数
     *
     * @param roleQO 查询条件对象
     * @return 统计值
     */
    @Override
    public Integer countRole(Role roleQO) {
        return roleMapper.countRole(roleQO);
    }

    /**
     * 保存一条 Role 表记录
     *
     * @param roleQO 查询条件对象
     * @return true|false
     */
    @Override
    public Boolean saveRole(Role roleQO) {
        Long roleId = roleQO.getRoleId();
        if (roleId != null) {
            return 1 == roleMapper.updateRole(roleQO);
        } else {
            return 1 == roleMapper.insertRole(roleQO);
        }
    }

    @Override
    public List<Permission> listPermissionByRoleId(Long roleId) {
        RolePermission rolePermissionQO = new RolePermission();
        rolePermissionQO.setRoleId(roleId);
        List<RolePermission> rolePermissions = rolePermissionMapper.listRolePermission(rolePermissionQO);
        if (rolePermissions != null) {
            List<Permission> permissionList = new ArrayList<>();
            for (RolePermission rolePermission : rolePermissions) {
                Permission permissionPO = permissionMapper.getPermissionById(rolePermission.getPermissionId());
                permissionList.add(permissionPO);
            }
            return permissionList;
        }
        return null;
    }
}