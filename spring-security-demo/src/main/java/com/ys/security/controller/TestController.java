package com.ys.security.controller;


import com.ys.security.domain.entity.User;
import com.ys.security.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created on 2020-04-17 16:03
 *
 * @author yusheng
 **/
@RestController
public class TestController {
    @Resource
    private UserService userService;

    @GetMapping("/test")
    public String test() {
        List<User> users = userService.listAllUser();
        System.out.println("users = " + users);
        return "success";
    }

    @PostMapping("/test")
    public String test2(Integer userId) {
        System.out.println("userId = " + userId);
        List<User> users = userService.listAllUser();
        System.out.println("users = " + users);
        return "success";
    }
}
