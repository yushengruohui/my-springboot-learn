package com.ys.shiro.service.impl;

import com.ys.shiro.dao.RoleDao;
import com.ys.shiro.dao.UserDao;
import com.ys.shiro.dao.UserRoleDao;
import com.ys.shiro.domain.entity.Role;
import com.ys.shiro.domain.entity.User;
import com.ys.shiro.domain.entity.UserRole;
import com.ys.shiro.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * User表业务层具体实现类
 *
 * @author yusheng
 * @create-time 2020-04-17 23:43:12
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Resource
    private RoleDao roleDao;

    /**
     * 获取 User 表所有记录
     *
     * @return userPOList
     */
    @Override
    public List<User> listAllUser() {
        return userDao.listAllUser();
    }

    /**
     * 通过主键获取一条 User 表记录
     *
     * @param userId User表主键
     * @return userPO
     */
    @Override
    public User getUserById(Long userId) {
        return userDao.getUserById(userId);
    }

    /**
     * 获取一条符合要求的 User 表记录
     *
     * @param userQO 查询条件对象
     * @return userPO
     */
    @Override
    public User getUser(User userQO) {
        return userDao.getUser(userQO);
    }

    /**
     * 获取 User 表中符合查询条件的所有记录
     *
     * @param userQO 查询条件对象
     * @return UserPOList
     */
    @Override
    public List<User> listUser(User userQO) {
        return userDao.listUser(userQO);
    }

    /**
     * 在 User 表中添加一条记录
     *
     * @param userQO 查询条件对象
     * @return true|false
     */
    @Override
    public Boolean insertUser(User userQO) {
        return 1 == userDao.insertUser(userQO);
    }

    /**
     * 在 User 表中修改一条记录
     *
     * @param userQO 主键不为空的查询条件对象
     * @return true|false
     */
    @Override
    public Boolean updateUser(User userQO) {
        return 1 == userDao.updateUser(userQO);
    }

    /**
     * 通过主键删除 User 表中的一条记录
     *
     * @param userId User表主键
     * @return true|false
     */
    @Override
    public Boolean deleteUserById(Long userId) {
        return 1 == userDao.deleteUserById(userId);
    }

    /**
     * 查询 User 表中是否存在符合查询条件的记录
     *
     * @param userQO 查询条件对象
     * @return true|false
     */
    @Override
    public Boolean existUser(User userQO) {
        return null != userDao.existUser(userQO);
    }

    /**
     * 统计 User 表中符合查询条件的记录条数
     *
     * @param userQO 查询条件对象
     * @return 统计值
     */
    @Override
    public Integer countUser(User userQO) {
        return userDao.countUser(userQO);
    }

    /**
     * 保存一条 User 表记录
     *
     * @param userQO 查询条件对象
     * @return true|false
     */
    @Override
    public Boolean saveUser(User userQO) {
        Long userId = userQO.getUserId();
        if (userId != null) {
            return 1 == userDao.updateUser(userQO);
        } else {
            return 1 == userDao.insertUser(userQO);
        }
    }

    @Override
    public List<Role> listUserRoleByUserId(Long userId) {
        UserRole userRoleQO = new UserRole();
        userRoleQO.setUserId(userId);
        List<UserRole> userRoles = userRoleDao.listUserRole(userRoleQO);
        List<Role> roleList = new ArrayList<>();
        if (userRoles != null) {
            for (UserRole userRole : userRoles) {
                Role rolePO = roleDao.getRoleById(userRole.getRoleId());
                roleList.add(rolePO);
            }
            return roleList;
        }
        return null;
    }
}