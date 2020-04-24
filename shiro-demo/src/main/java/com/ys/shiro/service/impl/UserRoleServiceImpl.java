package com.ys.shiro.service.impl;

import com.ys.shiro.domain.entity.UserRole;
import com.ys.shiro.dao.UserRoleDao;
import com.ys.shiro.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * UserRole表业务层具体实现类
 * 
 * @create-time 2020-04-17 23:43:31
 * @author yusheng
 */
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {
    
    @Resource
    private UserRoleDao userRoleDao;

    /**
     * 获取 UserRole 表所有记录
     *
     * @return userRolePOList
     */
    @Override
    public List<UserRole> listAllUserRole() {
        return userRoleDao.listAllUserRole();
    }
    
    /**
     * 通过主键获取一条 UserRole 表记录
     *
     * @param userRoleId UserRole表主键
     * @return userRolePO  
     */
    @Override
    public UserRole getUserRoleById(Long userRoleId) {
        return userRoleDao.getUserRoleById(userRoleId);
    }
    
    /**
     * 获取一条符合要求的 UserRole 表记录
     *
     * @param userRoleQO 查询条件对象
     * @return userRolePO  
     */
    @Override
    public UserRole getUserRole(UserRole userRoleQO) {
        return userRoleDao.getUserRole(userRoleQO);
    }
    
    /**
     * 获取 UserRole 表中符合查询条件的所有记录
     *
     * @param userRoleQO 查询条件对象
     * @return UserRolePOList
     */
    @Override
    public List<UserRole> listUserRole(UserRole userRoleQO) {
        return userRoleDao.listUserRole(userRoleQO);
    }
    
    /**
     * 在 UserRole 表中添加一条记录
     *
     * @param userRoleQO 查询条件对象
     * @return true|false
     */
    @Override
    public Boolean insertUserRole(UserRole userRoleQO) {
        return  1 == userRoleDao.insertUserRole(userRoleQO);
    }

    /**
     * 在 UserRole 表中修改一条记录
     *
     * @param userRoleQO 主键不为空的查询条件对象
     * @return true|false
     */
    @Override
    public Boolean updateUserRole(UserRole userRoleQO) {
        return 1 == userRoleDao.updateUserRole(userRoleQO);
    }

    /**
     * 通过主键删除 UserRole 表中的一条记录
     *
     * @param userRoleId UserRole表主键
     * @return true|false
     */
    @Override
    public Boolean deleteUserRoleById(Long userRoleId) {
        return 1 == userRoleDao.deleteUserRoleById(userRoleId);
    }
    
    /**
     * 查询 UserRole 表中是否存在符合查询条件的记录
     *
     * @param userRoleQO 查询条件对象
     * @return true|false
     */
    @Override
    public Boolean existUserRole(UserRole userRoleQO) {
        return null != userRoleDao.existUserRole(userRoleQO);
    }
    
    /**
     * 统计 UserRole 表中符合查询条件的记录条数
     *
     * @param userRoleQO 查询条件对象
     * @return 统计值
     */
    @Override
    public Integer countUserRole(UserRole userRoleQO) {
        return userRoleDao.countUserRole(userRoleQO);
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
            return 1 == userRoleDao.updateUserRole(userRoleQO);
        } else {
            return 1 == userRoleDao.insertUserRole(userRoleQO);
        }
    }
}