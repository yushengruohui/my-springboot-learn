package com.ys.mybatis.dao;

import com.ys.mybatis.domain.entity.Student;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Student表数据库数据访问层接口
 *
 * @create-time 2020-05-10 22:24:44
 * @author yusheng
 */
public interface StudentDao {

    /**
     * 获取 Student 表所有记录
     *
     * @return studentPOList
     */
    List<Student> listAllStudent();

    /**
     * 通过主键获取一条 Student 表记录
     *
     * @param studentId Student表主键
     * @return studentPO
     */
    Student getStudentById(Long studentId);

   /**
     * 获取一条符合要求的 Student 表记录
     *
     * @param studentQO 查询条件对象
     * @return studentPO
     */
    Student getStudent(Student studentQO);

    /**
     * 获取 Student 表中符合查询条件的所有记录
     *
     * @param studentQO 查询条件对象
     * @return StudentPOList
     */
    List<Student> listStudent(Student studentQO);

    /**
     * 在 Student 表中添加一条记录
     *
     * @param studentQO 查询条件对象
     * @return 1|0
     */
    Integer insertStudent(Student studentQO);

    /**
     * 在 Student 表中修改一条记录
     *
     * @param studentQO 主键不为空的查询条件对象
     * @return 1|0
     */
    Integer updateStudent(Student studentQO);

    /**
     * 通过主键删除 Student 表中的一条记录
     *
     * @param studentId Student表主键
     * @return 1|0
     */
    Integer deleteStudentById(Long studentId);

    /**
     * 删除 Student 表中所有符合查询条件的记录
     *
     * @param studentQO 查询条件对象（注意：属性全为空，则删除所有记录)
     * @return 删除记录的条数
     */
    Integer deleteStudent(Student studentQO);

    /**
     * 查询 Student 表是否存在符合查询条件的一条记录
     *
     * @param studentQO 查询条件对象
     * @return 1|null
     */
    Integer existStudent(Student studentQO);

    /**
     * 统计 Student 表中符合查询条件的记录条数
     *
     * @param studentQO 查询条件对象
     * @return 统计值
     */
    Integer countStudent(Student studentQO);
    
}