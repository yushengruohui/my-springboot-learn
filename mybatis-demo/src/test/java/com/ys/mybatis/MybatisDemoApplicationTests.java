package com.ys.mybatis;

import com.ys.mybatis.domain.entity.Student;
import com.ys.mybatis.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class MybatisDemoApplicationTests {

    @Resource
    private StudentService studentService;

    @Test
    void mybatisDemo1() {
        // List<Student> students = studentService.listAllStudent();
        Student student = new Student();
        student.setStudentId(1L);
        System.out.println("students = " + studentService.getStudent(student));
    }

    @Test
    void mybatisDemo2() {
        Student student = new Student();
        student.setStudentId(3L);
        student.setName("小明3");
        student.setGender(0);
        student.setStatus(0);
        student.setScore("56");
        studentService.insertStudent(student);
    }

    @Test
    void mybatisDemo3() {
        studentService.deleteStudentById((long) 1);
    }

}
