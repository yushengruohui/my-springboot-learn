package com.ys.security.service.impl;

import com.ys.security.domain.entity.UserRole;
import com.ys.security.dao.UserRoleMapper;
import com.ys.security.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * UserRole表业务层具体实现类
 * 
 * @create-time 2020-04-17 15:27:39
 * @author yusheng
 */
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {
    
    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 获取 UserRole 表所有记录
     *
     * @return userRolePOList
     */
    @Override
    public List<UserRole> listAllUserRole() {
        return userRoleMapper.listAllUserRole();
    }
    
    /**
     * 通过主键获取一条 UserRole 表记录
     *
     * @param userRoleId UserRole表主键
     * @return userRolePO  
     */
    @Override
    public UserRole getUserRoleById(Long userRoleId) {
        return userRoleMapper.getUserRoleById(userRoleId);
    }
    
    /**
     * 获取一条符合要求的 UserRole 表记录
     *
     * @param userRoleQO 查询条件对象
     * @return userRolePO  
     */
    @Override
    public UserRole getUserRole(UserRole userRoleQO) {
        return userRoleMapper.getUserRole(userRoleQO);
    }
    
    /**
     * 获取 UserRole 表中符合查询条件的所有记录
     *
     * @param userRoleQO 查询条件对象
     * @return UserRolePOList
     */
    @Override
    public List<UserRole> listUserRole(UserRole userRoleQO) {
        return userRoleMapper.listUserRole(userRoleQO);
    }
    
    /**
     * 在 UserRole 表中添加一条记录
     *
     * @param userRoleQO 查询条件对象
     * @return true|false
     */
    @Override
    public Boolean insertUserRole(UserRole userRoleQO) {
        return  1 == userRoleMapper.insertUserRole(userRoleQO);
    }

    /**
     * 在 UserRole 表中修改一条记录
     *
     * @param userRoleQO 主键不为空的查询条件对象
     * @return true|false
     */
    @Override
    public Boolean updateUserRole(UserRole userRoleQO) {
        return 1 == userRoleMapper.updateUserRole(userRoleQO);
    }

    /**
     * 通过主键删除 UserRole 表中的一条记录
     *
     * @param userRoleId UserRole表主键
     * @return true|false
     */
    @Override
    public Boolean deleteUserRoleById(Long userRoleId) {
        return 1 == userRoleMapper.deleteUserRoleById(userRoleId);
    }
    
    /**
     * 查询 UserRole 表中是否存在符合查询条件的记录
     *
     * @param userRoleQO 查询条件对象
     * @return true|false
     */
    @Override
    public Boolean existUserRole(UserRole userRoleQO) {
        return null != userRoleMapper.existUserRole(userRoleQO);
    }
    
    /**
     * 统计 UserRole 表中符合查询条件的记录条数
     *
     * @param userRoleQO 查询条件对象
     * @return 统计值
     */
    @Override
    public Integer countUserRole(UserRole userRoleQO) {
        return userRoleMapper.countUserRole(userRoleQO);
    }
    
    /**
     * 保存一条 UserRole 表记录
     *
     * @param userRoleQO 查询条件对象
     * @return true|false
     */
    @Override
    public Boolean saveUserRole(UserRole userRoleQO) {
        Long userRoleId = userRoleQO.getUserRoleId( );
        if (userRoleId != null) {
            return 1 == userRoleMapper.updateUserRole(userRoleQO);
        } else {
            return 1 == userRoleMapper.insertUserRole(userRoleQO);
        }
    }
}