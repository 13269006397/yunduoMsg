package com.yunduomsg.utils;

import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Random;
import java.util.UUID;

@Repository
public class NumberUtils {


    /**
     * 生成6位验证码
     * @return
     */
    public String randomCode(){
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    /**
     * UUid
     */
    public String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
