package com.xinqiu.stock.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(StockController.class);

    @RequestMapping("/")
    public String getHello() {
        logger.info("库存系统调用成功");

        return "hello";
    }

}
