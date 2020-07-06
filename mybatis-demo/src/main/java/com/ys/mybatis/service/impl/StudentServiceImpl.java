package com.ys.mybatis.service.impl;

import com.ys.mybatis.domain.entity.Student;
import com.ys.mybatis.dao.StudentDao;
import com.ys.mybatis.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Student表业务层具体实现类
 *
 * @author yusheng
 * Created on 2020-07-02 22:27:53
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService {
    //private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StudentServiceImpl.class);
    @Resource
    private StudentDao studentDao;

    @Override
    public List<Student> listAllStudent() {
        return studentDao.listAllStudent();
    }

    @Override
    public Student getStudentById(Long studentId) {
        return studentDao.getStudentById(studentId);
    }

    @Override
    public Student getStudent(Student student) {
        return studentDao.getStudent(student);
    }

    @Override
    public List<Student> listStudent(Student student) {
        return studentDao.listStudent(student);
    }

    @Override
    public Boolean insertStudent(Student student) {
        return  1 == studentDao.insertStudent(student);
    }

    @Override
    public Boolean updateStudent(Student student) {
        return 1 == studentDao.updateStudent(student);
    }

    @Override
    public Boolean deleteStudentById(Long studentId) {
        return 1 == studentDao.deleteStudentById(studentId);
    }

    @Override
    public Boolean deleteStudent(Student student) {
        return 0 != studentDao.deleteStudent(student);
    }

    @Override
    public Boolean existStudent(Student student) {
        return null != studentDao.existStudent(student);
    }

    @Override
    public Integer countStudent(Student student) {
        return studentDao.countStudent(student);
    }

    @Override
    public Boolean saveStudent(Student student) {
        Long studentId = student.getStudentId( );
        if (studentId != null) {
            return 1 == studentDao.updateStudent(student);
        } else {
            return 1 == studentDao.insertStudent(student);
        }
    }
    
}