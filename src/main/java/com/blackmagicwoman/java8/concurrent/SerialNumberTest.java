package com.blackmagicwoman.java8.concurrent;

/**
 * @program: mybatisTest
 * @description: 序列号测试
 * @author: heise
 * @create: 2023-08-08 22:10
 **/
public class SerialNumberTest {
    public static void main(String[] args) {
        System.out.println(7^6^5);
        SerialNumberChecker.test(new SerialNumbers());
    }
}
