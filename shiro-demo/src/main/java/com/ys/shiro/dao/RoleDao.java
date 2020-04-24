package com.ys.shiro.dao;

import com.ys.shiro.domain.entity.Role;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Role表数据库数据访问层接口
 * 
 * @create-time 2020-04-17 23:42:37
 * @author yusheng
 */
public interface RoleDao {

    /**
     * 获取 Role 表所有记录
     *
     * @return rolePOList
     */
    List<Role> listAllRole();
    
    /**
     * 通过主键获取一条 Role 表记录
     *
     * @param roleId Role表主键
     * @return rolePO  
     */
    Role getRoleById(Long roleId);
    
   /**
     * 获取一条符合要求的 Role 表记录
     *
     * @param roleQO 查询条件对象
     * @return rolePO  
     */
    Role getRole(Role roleQO);
    
    /**
     * 获取 Role 表中符合查询条件的所有记录
     *
     * @param roleQO 查询条件对象
     * @return RolePOList
     */
    List<Role> listRole(Role roleQO);
    
    /**
     * 在 Role 表中添加一条记录
     *
     * @param roleQO 查询条件对象
     * @return 1|0
     */
    Integer insertRole(Role roleQO);

    /**
     * 在 Role 表中修改一条记录
     *
     * @param roleQO 主键不为空的查询条件对象
     * @return 1|0
     */
    Integer updateRole(Role roleQO);

    /**
     * 通过主键删除 Role 表中的一条记录
     *
     * @param roleId Role表主键
     * @return 1|0
     */
    Integer deleteRoleById(Long roleId);

    /**
     * 查询 Role 表是否存在符合查询条件的一条记录
     *
     * @param roleQO 查询条件对象
     * @return 1|null
     */
    Integer existRole(Role roleQO);
    
    /**
     * 统计 Role 表中符合查询条件的记录条数
     *
     * @param roleQO 查询条件对象
     * @return 统计值
     */
    Integer countRole(Role roleQO);
}