package com.ys.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author yusheng
 * Created on 2020-05-10 19:19
 **/
@RestController
@RequestMapping("/")
public class WebTestController {
    //private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WebTestController.class);

    @GetMapping
    public String get() {
        System.out.println("true = " + true);
        return "success";
    }


}
