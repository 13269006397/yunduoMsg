package com.xinqiu.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/order")
public class orderController {


    @RequestMapping("/getGoodsByUserId")
    String getGoodsByUserId(String userId){

        userId = "颜文博";
        return userId + "购买了士力架";
    }

    // order模块获取用户信息 restTemplate 远程调用
    @RequestMapping("/getUserbyOrder")
    List getUserbyOrder(){

        //请求地址
        String url = "http://localhost:8081/user/findAllUser/";
        RestTemplate restTemplate = new RestTemplate();
        List userList = restTemplate.getForObject(url, List.class);
        return userList;

    }
}
