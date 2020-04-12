package com.ys.mybatis.domain.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * (Student)表实体类
 *
 * @author yusheng
 * @since 2020-04-12 17:37:58
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
        StringBuffer sb=new StringBuffer(300);
        sb.append("{");
        sb.append(" ").append("studentId").append(" : ");
        sb.append(studentId).append(" ,");
        sb.append(" ").append("name").append(" : ");
        sb.append("\"").append(name).append("\" ,");
        sb.append(" ").append("gender").append(" : ");
        sb.append(gender).append(" ,");
        sb.append(" ").append("status").append(" : ");
        sb.append(status).append(" ,");
        sb.append(" ").append("score").append(" : ");
        sb.append("\"").append(score).append("\" ,");
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("}");
        return sb.toString();
    }
}