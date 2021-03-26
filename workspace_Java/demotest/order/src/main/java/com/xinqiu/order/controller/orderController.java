package com.xinqiu.order.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/order")
public class orderController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(orderController.class);

    @Autowired
    private RestTemplate restTemplate;



    @RequestMapping("/getGoodsByUserName")
    String getGoodsByUserId(String userName){

        userName = "颜文博";
        return userName + "购买了士力架";
    }

    // 订单模块 调用 库存模块减少库存
    @RequestMapping("/orderStockReduce")
    String getUserbyOrder(String stockName){
        // naCos地址
        String url = "http://stock-service/stock/stockReduce";
        String result = restTemplate.getForObject(url, String.class);
        logger.info(result);
        return result;
    }
}
