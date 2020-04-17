package com.ys.security.dao;

import com.ys.security.domain.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * User表数据库访问层接口
 * 
 * @create-time 2020-04-17 15:39:32
 * @author yusheng
 */
public interface UserMapper {

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
     * @return 1|0
     */
    Integer insertUser(User userQO);

    /**
     * 在 User 表中修改一条记录
     *
     * @param userQO 主键不为空的查询条件对象
     * @return 1|0
     */
    Integer updateUser(User userQO);

    /**
     * 通过主键删除 User 表中的一条记录
     *
     * @param userId User表主键
     * @return 1|0
     */
    Integer deleteUserById(Long userId);

    /**
     * 查询 User 表是否存在符合查询条件的一条记录
     *
     * @param userQO 查询条件对象
     * @return 1|null
     */
    Integer existUser(User userQO);
    
    /**
     * 统计 User 表中符合查询条件的记录条数
     *
     * @param userQO 查询条件对象
     * @return 统计值
     */
    Integer countUser(User userQO);
    
    
}