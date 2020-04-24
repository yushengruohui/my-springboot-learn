package com.ys.shiro.config.shiro3.token;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * Created on 2020-04-23 17:53
 *
 * @author yusheng
 **/
class MyJwtTokenTest {

    @Test
    void listToStr() {
        List<String> roles = new ArrayList<>();
        roles.add("qwer");
        roles.add("111");
        roles.add("222");
        roles.add("2222");
        String s = roles.toString();
        System.out.println("roles = " + s);
    }

    @Test
    void mapToStr() {
        Map<String, Set<String>> tempMap = new HashMap<>();
        Set<String> temp = tempMap.get("temp");
        Set<String> roles = new HashSet<>();
        roles.add("qwer");
        roles.add("111");
        roles.add("222");
        roles.add("2222");
        tempMap.put("temp", roles);
        temp.add("axdw");
        tempMap.put("temp", temp);
        System.out.println(tempMap);
    }
}