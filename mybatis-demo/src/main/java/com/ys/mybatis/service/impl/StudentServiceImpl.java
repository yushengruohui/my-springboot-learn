package com.ys.mybatis.service.impl;

import com.ys.mybatis.domain.entity.Student;
import com.ys.mybatis.dao.StudentDao;
import com.ys.mybatis.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Student)表服务实现类
 *
 * @author yusheng
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService {
    
    @Resource
    private StudentDao studentDao;

    /**
     * 获取 Student 表所有记录
     *
     * @return studentPOList
     */
    @Override
    public List<Student> listAllStudent() {
        return studentDao.listAllStudent();
    }
    
    /**
     * 通过主键获取一条 Student 表记录
     *
     * @param studentId Student表主键
     * @return studentPO  
     */
    @Override
    public Student getStudentById(Long studentId) {
        return studentDao.getStudentById(studentId);
    }
    
    /**
     * 获取一条符合要求的 Student 表记录
     *
     * @param studentQO 查询条件对象
     * @return studentPO  
     */
    @Override
    public Student getStudent(Student studentQO) {
        return studentDao.getStudent(studentQO);
    }
    
    /**
     * 获取 Student 表中符合查询条件的所有记录
     *
     * @param studentQO 查询条件对象
     * @return StudentPOList
     */
    @Override
    public List<Student> listStudent(Student studentQO) {
        return studentDao.listStudent(studentQO);
    }
    
    /**
     * 在 Student 表中添加一条记录
     *
     * @param studentQO 查询条件对象
     * @return true|false
     */
    @Override
    public Boolean insertStudent(Student studentQO) {
        return  1 == studentDao.insertStudent(studentQO);
    }

    /**
     * 在 Student 表中修改一条记录
     *
     * @param studentQO 主键不为空的查询条件对象
     * @return true|false
     */
    @Override
    public Boolean updateStudent(Student studentQO) {
        return 1 == studentDao.updateStudent(studentQO);
    }

    /**
     * 通过主键删除 Student 表中的一条记录
     *
     * @param studentId Student表主键
     * @return true|false
     */
    @Override
    public Boolean deleteStudentById(Long studentId) {
        return 1 == studentDao.deleteStudentById(studentId);
    }
    
    /**
     * 查询 Student 表中是否存在符合查询条件的记录
     *
     * @param studentQO 查询条件对象
     * @return true|false
     */
    @Override
    public Boolean existStudent(Student studentQO) {
        return null != studentDao.existStudent(studentQO);
    }
    
    /**
     * 统计 Student 表中符合查询条件的记录条数
     *
     * @param studentQO 查询条件对象
     * @return 统计值
     */
    @Override
    public Integer countStudent(Student studentQO) {
        return studentDao.countStudent(studentQO);
    }
    
    /**
     * 保存一条 Student 表记录
     *
     * @param studentQO 查询条件对象
     * @return true|false
     */
    @Override
    public Boolean saveStudent(Student studentQO) {
        Long studentId = studentQO.getStudentId( );
        if (studentId != null) {
            return 1 == studentDao.updateStudent(studentQO);
        } else {
            return 1 == studentDao.insertStudent(studentQO);
        }
    }
}