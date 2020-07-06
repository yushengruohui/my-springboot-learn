package com.ys.controller;

import com.ys.domain.entity.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/**
 * @author yusheng
 * Created on 2020-07-05 23:05
 **/
@RestController
@RequestMapping("/redis")
public class RedisController {
    //private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RedisController.class);
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String, User> redisTemplate;

    @GetMapping
    public void get() {
        String userInfoStr = stringRedisTemplate.opsForValue().get("1");
        System.out.println("userInfoStr = " + userInfoStr);
        String age = stringRedisTemplate.opsForValue().get("user:age");
        System.out.println("age = " + age);
        User user = redisTemplate.opsForValue().get("user1");
        System.out.println("user = " + user);
    }

    @PostMapping
    public void add() {
        // 分开存储
        stringRedisTemplate.opsForValue().set("1", "我真的无语了");
        stringRedisTemplate.opsForValue().increment("user:age", 1L);
        // 定时失效
        stringRedisTemplate.opsForValue().set("user2", "我真的无语了2", 10, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("user1", User.builder().id(1).age(18).name("张三").build());
    }

    @PutMapping
    public void update(User user) {
        stringRedisTemplate.opsForValue().increment("user:age", 20L);
        stringRedisTemplate.opsForValue().decrement("user:age", 2L);
    }

    @DeleteMapping
    public void delete(Integer id) {
        stringRedisTemplate.delete("user:age");
        redisTemplate.delete("user1");
        System.out.println("id = " + id);
    }
}
