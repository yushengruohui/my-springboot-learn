package com.ys.mybatis.domain.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * [Student]表实体类
 *
 * @author yusheng
 * Created on 2020-07-02 22:27:53
 */
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long studentId;

    private String name;

    private Integer gender;

    private Integer status;

    private String score;
        
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
        
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
        
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
        
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
        
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", status=" + status +
                ", score='" + score + '\'' +
                "}";
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(studentId, name, gender, status, score);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId) && Objects.equals(name, student.name) && Objects.equals(gender, student.gender) && Objects.equals(status, student.status) && Objects.equals(score, student.score);
    }
    
    public Student() {
        super();
    }
    
    public static StudentBuilder builder() {
        StudentBuilder studentBuilder = new StudentBuilder();
        studentBuilder.student = new Student();
        return studentBuilder;
    }

    public static StudentBuilder builder(Student student) {
        StudentBuilder studentBuilder = new StudentBuilder();
        studentBuilder.student = student == null ? new Student() : student;
        return studentBuilder;
    }
    
    public static class StudentBuilder {

        private Student student;        

        public StudentBuilder studentId(Long studentId) {
            student.studentId = studentId;
            return this;
        }

        public StudentBuilder name(String name) {
            student.name = name;
            return this;
        }

        public StudentBuilder gender(Integer gender) {
            student.gender = gender;
            return this;
        }

        public StudentBuilder status(Integer status) {
            student.status = status;
            return this;
        }

        public StudentBuilder score(String score) {
            student.score = score;
            return this;
        }

        public Student build() {
            return this.student;
        }

    }
    
}