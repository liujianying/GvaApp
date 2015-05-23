package com.javatest;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.atomic.AtomicInteger;

public class JavaTest {

    static AtomicInteger mSerialNumGenerator = new AtomicInteger(0);
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

//        Schema schema = new Schema(1, "com.jredu.entity");
//        //实体类
//        Entity employee = schema.addEntity("Employee");
//        employee.addIdProperty(); //主键
//        employee.addStringProperty("name");
//        employee.addDateProperty("hireDate");
//        //生成
//        new  DaoGenerator()
//                .generateAll(schema, "/Users/liujianying/GvaApp/javatest/src/main/java/com/ydh/gva/core");
        String region_cd_string = ((int)(134567/100))*100 + "";
        JSONObject json = new JSONObject();
        json.put("A", "a");

        System.out.println(region_cd_string);

    }

    /**
     * 为每个请求生成一个系列号
     *
     * @return 序列号
     */
    public static int generateSerialNumber() {
        return mSerialNumGenerator.incrementAndGet();
    }
}
