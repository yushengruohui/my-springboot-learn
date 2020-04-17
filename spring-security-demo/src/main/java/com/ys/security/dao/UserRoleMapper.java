package com.ys.security.dao;

import com.ys.security.domain.entity.UserRole;

import java.util.List;

/**
 * UserRole表数据库访问层接口
 *
 * @author yusheng
 * @create-time 2020-04-17 15:27:39
 */
public interface UserRoleMapper {

    /**
     * 获取 UserRole 表所有记录
     *
     * @return userRolePOList
     */
    List<UserRole> listAllUserRole();

    /**
     * 通过主键获取一条 UserRole 表记录
     *
     * @param userRoleId UserRole表主键
     * @return userRolePO
     */
    UserRole getUserRoleById(Long userRoleId);

    /**
     * 获取一条符合要求的 UserRole 表记录
     *
     * @param userRoleQO 查询条件对象
     * @return userRolePO
     */
    UserRole getUserRole(UserRole userRoleQO);

    /**
     * 获取 UserRole 表中符合查询条件的所有记录
     *
     * @param userRoleQO 查询条件对象
     * @return UserRolePOList
     */
    List<UserRole> listUserRole(UserRole userRoleQO);

    /**
     * 在 UserRole 表中添加一条记录
     *
     * @param userRoleQO 查询条件对象
     * @return 1|0
     */
    Integer insertUserRole(UserRole userRoleQO);

    /**
     * 在 UserRole 表中修改一条记录
     *
     * @param userRoleQO 主键不为空的查询条件对象
     * @return 1|0
     */
    Integer updateUserRole(UserRole userRoleQO);

    /**
     * 通过主键删除 UserRole 表中的一条记录
     *
     * @param userRoleId UserRole表主键
     * @return 1|0
     */
    Integer deleteUserRoleById(Long userRoleId);

    /**
     * 查询 UserRole 表是否存在符合查询条件的一条记录
     *
     * @param userRoleQO 查询条件对象
     * @return 1|null
     */
    Integer existUserRole(UserRole userRoleQO);

    /**
     * 统计 UserRole 表中符合查询条件的记录条数
     *
     * @param userRoleQO 查询条件对象
     * @return 统计值
     */
    Integer countUserRole(UserRole userRoleQO);

}