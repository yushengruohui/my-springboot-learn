package com.ys.security.service;

import com.ys.security.domain.entity.Role;
import com.ys.security.domain.entity.User;

import java.util.List;

/**
 * User表业务层接口
 *
 * @author yusheng
 * @create-time 2020-04-17 15:39:32
 */
public interface UserService {

    /**
     * 获取 User 表所有记录
     *
     * @return userPOList
     */
    List<User> listAllUser();

    /**
     * 通过主键获取一条 User 表记录
     *
     * @param userId User表主键
     * @return userPO
     */
    User getUserById(Long userId);

    /**
     * 获取一条符合要求的 User 表记录
     *
     * @param userQO 查询条件对象
     * @return userPO
     */
    User getUser(User userQO);

    /**
     * 获取 User 表中符合查询条件的所有记录
     *
     * @param userQO 查询条件对象
     * @return UserPOList
     */
    List<User> listUser(User userQO);

    /**
     * 在 User 表中添加一条记录
     *
     * @param userQO 查询条件对象
     * @return true|false
     */
    Boolean insertUser(User userQO);

    /**
     * 在 User 表中修改一条记录
     *
     * @param userQO 主键不为空的查询条件对象
     * @return true|false
     */
    Boolean updateUser(User userQO);

    /**
     * 通过主键删除 User 表中的一条记录
     *
     * @param userId User表主键
     * @return true|false
     */
    Boolean deleteUserById(Long userId);

    /**
     * 查询 User 表中是否存在符合查询条件的记录
     *
     * @param userQO 查询条件对象
     * @return true|false
     */
    Boolean existUser(User userQO);

    /**
     * 统计 User 表中符合查询条件的记录条数
     *
     * @param userQO 查询条件对象
     * @return 统计值
     */
    Integer countUser(User userQO);

    /**
     * 保存一条 User 表记录
     *
     * @param userQO 查询条件对象
     * @return true|false
     */
    Boolean saveUser(User userQO);

    List<Role> listUserRoleByUserId(Long userId);
}