package com.xinqiu.usermsg.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户模块
 */
@RestController
@RequestMapping("/user")
public class userController {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(userController.class);

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/")
    public String getHello() {

        logger.info("用户模块启动");
        return "hello";
    }

    // 获取所有用户名
    @RequestMapping(value = "/findAllUser")
    List<String> findAllUser(){
        ArrayList<String> userList = new ArrayList<>();
        userList.add("张三");
        userList.add("李四");
        userList.add("王五");
        userList.add("冯六");
        System.out.println("--- 被order-service调用 ---");
        return userList;
    }

    // user模块调用order  restTemplate调用
    @RequestMapping("/getOrder")
    String getOrder(){
        // 请求地址
        // String url = "http://localhost:8082/order/getGoodsByUserId/";
        String url = "http://order-service/order/getGoodsByUserId/";
        String result = restTemplate.getForObject(url, String.class);
        return result;
    }

}