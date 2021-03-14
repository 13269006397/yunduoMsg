package com.yunduomsg.utils;

import org.junit.Test;

import java.io.*;

/**
 * 文件工具类
 */
public class FileUtils {


    // 读取文件内容
    @Test
    public void getFileTextByfilePath(){
        String filePath = "D:/测试文件.txt";

        String StringText = "";

        try {
            // 1. 获取文件
            File file = new File(filePath);
            // 2. 获取文件字节流
            FileInputStream fileInputStream = new FileInputStream(file);
            // 3. 转为字符流
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            // 4. BufferedReader提供读取文本的方法 readLine 一次读取一行
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            // 5. 拼接获取全部内容
            StringBuffer sb = new StringBuffer();
            String FileText = null;
            while ((FileText = bufferedReader.readLine()) != null){
                // 增加换行符标志
                FileText+='\n';
                sb.append(FileText);
            }
            StringText = sb.toString();


        }catch (IOException e){
            System.out.println("读取文件错误");
        }
        System.out.println(StringText);
    }
}
