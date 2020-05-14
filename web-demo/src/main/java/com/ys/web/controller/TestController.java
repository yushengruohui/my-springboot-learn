package com.ys.web.controller;

import com.ys.web.domain.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 测试spring默认接受参数
 *
 * @author yusheng
 * Created on 2020-05-10 19:17
 **/
@RestController
@RequestMapping("/test")
public class TestController {
    //private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TestController.class);

    @GetMapping("")
    public String getStudent() {
        // 测试连接
        return "success";
    }

    @GetMapping("/param")
    public Student getStudent(Student student) {
        // 测试get接受参数
        System.out.println(student);
        // 可以接受 contentType = multipart/form-data || null 格式数据，其他contentType不报错，但无接受数据
        return student;
    }

    @GetMapping("/param2")
    public Student getStudent1(@RequestBody Student student) {
        // 测试get接受参数
        System.out.println(student);
        // 可以接受 contentType = application/json 格式数据，其他contentType 报错DefaultHandlerExceptionResolver
        return student;
    }

    @PostMapping("/param")
    public Student addStudent(Student student) {
        System.out.println("post => " + student);
        // 可以接受 contentType = multipart/form-data || null || application/x-www-form-urlencoded 格式数据，其他contentType不报错，但无接受数据
        return student;
    }

    @PostMapping("/param2")
    public Student addStudent2(@RequestBody Student student) {
        System.out.println("post => " + student);
        // 可以接受 contentType = application/json 格式数据，其他contentType 报错DefaultHandlerExceptionResolver
        return student;
    }
}
