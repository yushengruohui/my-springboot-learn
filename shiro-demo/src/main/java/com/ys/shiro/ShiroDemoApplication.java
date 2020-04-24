package com.ys.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 开启servlet的组件扫描 ，用于 @WebFilter 注册过滤器到 spring 容器
@SpringBootApplication
@MapperScan("com.ys.shiro.dao")
public class ShiroDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroDemoApplication.class, args);
    }

}
