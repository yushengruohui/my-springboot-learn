package com.ys.shiro.controller;


import com.ys.shiro.domain.entity.User;
import com.ys.shiro.service.UserService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created on 2020-04-18 19:49
 *
 * @author yusheng
 **/
@RestController
public class TestController {
    @Resource
    private UserService userService;

    @GetMapping("/test")
    public String test(String test) {
        System.out.println("test = " + test);
        return test;
    }

    @PostMapping("/test")
    @RequiresRoles("vip")
    public String test3(User user) {
        System.out.println("user = " + user);
        return "success";
    }

    @GetMapping("/test2")
    public String test2(User user) {
        System.out.println("user = " + user);
        return "success";
    }
}
