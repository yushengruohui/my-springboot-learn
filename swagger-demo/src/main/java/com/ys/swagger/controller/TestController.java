package com.ys.swagger.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yusheng
 * Created on 2020-04-28 23:24
 **/
@RestController
@RequestMapping("/test")
public class TestController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TestController.class);

    @GetMapping
    public String test(String test) {
        log.info("test = {}", test);
        return "false";
    }


}
