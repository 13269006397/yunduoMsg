package com.xinqiu.stock.controller;

import com.xinqiu.stock.utils.RedisUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/stock")
public class StockController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(StockController.class);

    @Autowired
    private RedisUtils redisUtils;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private Redisson redisson;

    @RequestMapping("/")
    public String getHello() {
        logger.info("---库存系统调用成功---");
        return "hello";
    }

    // 减少库存
    @RequestMapping("/stockReduce")
    public String stockReduce(String stockName) {
        if (null == stockName || "".equals(stockName)){
            stockName = "奶茶";
        }
        // 分布式锁 redis实现
        String lockKey = "stockSystem";
        /*   String clientId = UUID.randomUUID().toString();
        Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, clientId,10, TimeUnit.SECONDS);
        if (!result){
            return "errorCode";
        }*/
        RLock redissonLock = redisson.getLock(lockKey);

        logger.info("--- 调用库存系统 ---");
        //查询缓存中是否存在
        boolean hasKey = redisUtils.excits(stockName);
        logger.info("--- 商品" + stockName + "存在校验:" + hasKey + " ---");
        try{
            // 加锁代码
            redissonLock.lock();
            if (hasKey) {
                String stockNumberString = redisUtils.getKey(stockName);
                int stockNumberInt;
                try {
                    stockNumberInt = Integer.parseInt(stockNumberString);
                } catch (ClassCastException e) {
                    return "商品数量转换错误";
                }
                if (stockNumberInt > 0) {
                    // 做减库存操作
                    int resultNumber = stockNumberInt - 1;
                    redisUtils.setKeyM(stockName, resultNumber, 30);
                    logger.info("--- 商品"+stockName+"库存为:"+resultNumber+" ---");
                    logger.info("--- 抢购成功 ---");
                    return "抢购成功";
                } else {
                    logger.info("--- 库存不足,操作失败 ---");
                    return "库存不足";
                }
            }
        }finally {
            redissonLock.unlock();
           /* if (clientId.equals(redisTemplate.opsForValue().get(lockKey))){
                redisTemplate.delete(lockKey);
            }*/
        }
        return "库存流程结束";
    }
}
