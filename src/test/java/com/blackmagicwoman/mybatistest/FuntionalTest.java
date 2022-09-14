package com.blackmagicwoman.mybatistest;

import com.blackmagicwoman.mybatistest.service.HandlerStringService;

/**
 * @program: mybatisTest
 * @description: 参数化函数的测试
 * @author: Fuwen
 * @create: 2022-09-14 21:48
 **/
public class FuntionalTest {
    public static void main(String[] args) {
        HandlerStringService handlerStringService = new HandlerStringService();
        String a="1234567890";
        String s1 = handlerStringService.handlerString(a, (s) -> s + "0987654321");
        System.out.println(s1);
    }
}
