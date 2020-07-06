package com.ys.mybatis.dao;

import com.ys.mybatis.domain.entity.Student;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Student表数据库数据访问层接口
 *
 * @author yusheng
 * Created on 2020-07-02 22:27:53
 */
public interface StudentDao {

    /**
     * 获取 Student 表所有记录
     *
     * @return Student 表所有记录
     */
    List<Student> listAllStudent();

    /**
     * 通过主键获取一条 Student 表记录
     *
     * @param studentId Student表主键
     * @return 和主键对应的 Student 表记录||null
     */
    Student getStudentById(Long studentId);

   /**
     * 获取一条符合查询条件的 Student 表记录
     *
     * @param student 查询条件对象
     * @return 一条符合查询条件的 Student 表记录
     */
    Student getStudent(Student student);

    /**
     * 获取 Student 表中所有符合查询条件的记录
     *
     * @param student 查询条件对象
     * @return Student 表中所有符合查询条件的记录
     */
    List<Student> listStudent(Student student);

    /**
     * 在 Student 表中添加一条记录
     *
     * @param student 查询条件对象
     * @return 1|0
     */
    Integer insertStudent(Student student);

    /**
     * 在 Student 表中修改一条记录
     *
     * @param student 主键不为空的查询条件对象
     * @return 1|0
     */
    Integer updateStudent(Student student);

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
     * @param student 查询条件对象（注意：属性全为空，则删除所有记录)
     * @return 删除记录的条数
     */
    Integer deleteStudent(Student student);

    /**
     * 查询 Student 表是否存在符合查询条件的一条记录
     *
     * @param student 查询条件对象
     * @return 1|null
     */
    Integer existStudent(Student student);

    /**
     * 统计 Student 表中符合查询条件的记录条数
     *
     * @param student 查询条件对象
     * @return 统计值
     */
    Integer countStudent(Student student);
    
}