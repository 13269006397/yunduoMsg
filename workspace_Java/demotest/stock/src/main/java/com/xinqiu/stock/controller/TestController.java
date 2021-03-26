package com.xinqiu.stock.controller;

import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;

public class TestController {


    /**
     * 查看Java对象布局
     */
    @Test
    void test01(){
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }


}
