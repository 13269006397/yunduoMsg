package com.yunduomsg.common;

import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 测试类
 */
@Component
public class CommonTest {

    @Scheduled(cron = "0/5 * * * * ?")
    public void execute(){
        System.out.println("我是定时执行的job"+ new Date());
    }

    @Test
    public void testw22(){
        execute();
    }


    @Test
    public void getArrayTest(){
        int[] intArray= {1,23,4,55,67};
        System.out.println(intArray.length);

        String[] StringArray = {"马强", "大肥猫", "莫王宇"};
        for (int i = 0; i < StringArray.length; i++) {
            System.err.println(StringArray[i]);
        }

        for (int number : intArray){
            System.out.println(number);
        }

    }


    @Test
    public void indexOfTest(){
        long start = System.currentTimeMillis();
        System.out.println(start);

        String localhost = "66.6.17";
        String aalenth = "66.6.17,66.6.6.18";
        int i = aalenth.indexOf(localhost);
        System.out.println(i);
        for (int i1 = 0; i1 < 200; i1++) {
            System.out.println(i1);
        }

        long end = System.currentTimeMillis();

        long indTime = end - start;
        String s = String.valueOf(indTime);

        System.err.println("接口耗时" + s + "ms");

    }

    @Test
    public void springTask(){
        String localhost = "66.6.17";
        String aalenth = "66.6.17,66.6.6.18";
        int i = aalenth.indexOf(localhost);
        System.out.println(i);

    }

    /**
     * 谁拿保时捷
     * @return
     */
    @Test
    public void whoGetPoshi() throws InterruptedException {
        String name1 = "颜文博";
        String name2 = "对手";

        // 默认对手一个人没邀请
        int pinduoduoFans = 0;
        // 颜文博已经邀请了两个人
        int yanwenboFans = 2;
        // 剩余时间
        int min = 3;

        for (int i = 0; i < 3; i++) {
            yanwenboFans += i;
            pinduoduoFans += i;
            System.out.println(name1+"现在邀请了"+yanwenboFans+"个人");
            System.out.println(name2+"现在邀请了"+pinduoduoFans+"个人");
            System.err.println("时间剩余"+ min--);
            Thread.sleep(2000);
        }

        System.out.println("时间快到了 颜文博领先"+ (yanwenboFans-pinduoduoFans)+"个人");
        if (yanwenboFans > pinduoduoFans){

            System.out.println("给对手+50000000个人");
            pinduoduoFans +=50000000;
        }

        System.out.println("时间到,恭喜 对手以"+(pinduoduoFans-yanwenboFans)+"的微弱票数拿到保时捷");
    }


    @Test
    public void test123(){


        String ss1 = "layer.alert(\"&#x4fee;&#x6539;&#x5bc6;&#x7801;&#x6210;&#x529f;\", {btn:[\"确定\"],closeBtn:0}, jump );";

        String ss2 = "&#x4fee;&#x6539;&#x5bc6;&#x7801;&#x6210;&#x529f;";

        int i = ss1.indexOf(ss2);
        System.out.println(i);

    }

    @Test
    public void test124(){
        int bline = 50;
        int count;
        System.out.println("每批次处理笔数" + bline);

        int i = 200;

        int startInt = i / bline;
        int addInt = i % bline == 0 ? 0 : 1;

        count = startInt + addInt;
        System.out.println("总笔数"+ i + "，待处理批次"+ count);

    }

    @Test
    public void test125(){
        ArrayList<String> list = new ArrayList<>();

        list.add("4104821995332556");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("1");
        list.add("4104821995332556");


        StringBuffer errorMessage =  new StringBuffer();

        errorMessage.append("log.info.去除重复身份证: ");

        for (int i = 0; i < list.size() - 1;i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    errorMessage.append(list.get(j) + " ");
                    list.remove(j);
                }
            }
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        String s = errorMessage.toString();
        System.out.println(s);

    }

    @Test
    public void test12345(){
        // 1.0 用集合创建流
        // 创建集合
        List<String> list = Arrays.asList("张三", "李四", "王五");
        // 创建一个串行流
        Stream<String> stream = list.stream();
        // 创建一个并行流
        Stream<String> parallelStream = list.parallelStream();
        // 2.0
        // 创建数组
        String[] persons = {"张三", "李四", "王五"};
        // 创建一个串行流
        Stream<String> stream1 = Arrays.stream(persons);
        // 创建一个并行流
        Stream<String> parallelStream2 = Arrays.stream(persons).parallel();
        Stream<String> stream2 = Stream.of("张三", "李四", "王五");
        // 输出5个奇数 1 3 5 7 9
        Stream<Integer> stream3 = Stream.iterate(1, x -> x + 2).limit(5);
        stream3.forEach(System.out::println);
        // 生成2个UUID
        Stream<UUID> stream4 = Stream.generate(UUID::randomUUID).limit(2);
        stream4.forEach(System.out::println);





    }


    @Test
    public void test111(){
        String code = "A2.0.012345678912345678912345678999120200213056699D202000010000100000000";
        String resultFlag = code.substring(35, 36);

        String code1= ",2232312323";
        String code122 = code1.substring(1);
        System.out.println(code122);


    }

    @Test
    public void testList3(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        System.out.println("删除前：" + list);

        Iterator<Integer> it = list.iterator();
        while (it.hasNext()){
            if(it.next().equals(2)){
                it.remove();
            }
        }
        System.out.println("删除后：" + list);
    }


    /**
     * 身份证校验
     * @param identityCode
     * @return
     */
    // 身份证校验码
    private static final int[] COEFFICIENT_ARRAY = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    // 身份证号的尾数规则
    private static final String[] IDENTITY_MANTISSA = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};

    private static final String IDENTITY_PATTERN = "^[0-9]{17}[0-9Xx]$";

    public static boolean isLegalPattern(String identity) {
        if (identity == null) {
            return false;
        }

        if (identity.length() != 18) {
            return false;
        }

        if (!identity.matches(IDENTITY_PATTERN)) {
            return false;
        }

        char[] chars = identity.toCharArray();
        long sum = IntStream.range(0, 17).map(index -> {
            char ch = chars[index];
            int digit = Character.digit(ch, 10);
            int coefficient = COEFFICIENT_ARRAY[index];
            return digit * coefficient;
        }).summaryStatistics().getSum();

        // 计算出的尾数索引
        int mantissaIndex = (int) (sum % 11);
        String mantissa = IDENTITY_MANTISSA[mantissaIndex];

        String lastChar = identity.substring(17);
        if (lastChar.equalsIgnoreCase(mantissa)) {
            return true;
        } else {
            return false;
        }
    }

    @Test
    public void test123223(){
        String name = "誰的";
        String name2 = "";
        name2 = name;
        System.out.println("name:" + name + ",name2:" + name2);



    }








}
