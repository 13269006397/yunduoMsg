package com.xinqiu.usermsg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component // 读取springbootApplication注解配置
public class timerController {

    @Autowired
    userController userController;

    @Configuration      //1.主要用于标记配置类，兼备Component的效果。
    @EnableScheduling   // 2.开启定时任务
    public class SaticScheduleTask {
        //3.添加定时任务
        @Scheduled(cron = "0 0 1 * * ?")
        //或直接指定时间间隔，例如：5秒
        //@Scheduled(fixedRate=5000)
        private void configureTasks() {
            List<String> allUser = userController.findAllUser();

            for (String userName: allUser){
                System.out.println(userName);
            }
        }
    }


    //秒（0~59） 例如0/5表示每5秒
    //分（0~59）
    //时（0~23）
    //日（0~31）的某天，需计算
    //月（0~11）
    //周几（ 可填1-7 或 SUN/MON/TUE/WED/THU/FRI/SAT）

    //// Cron表达式范例：
    //每隔5秒执行一次：*/5 * * * * ?
    //每隔1分钟执行一次：0 */1 * * * ?
    //每天23点执行一次：0 0 23 * * ?
    //每天凌晨1点执行一次：0 0 1 * * ?
    //每月1号凌晨1点执行一次：0 0 1 1 * ?
    //每月最后一天23点执行一次：0 0 23 L * ?
    //每周星期天凌晨1点实行一次：0 0 1 ? * L
    //在26分、29分、33分执行一次：0 26,29,33 * * * ?
    //每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?
}
