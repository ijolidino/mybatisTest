package com.blackmagicwoman.mybatistest;

import com.blackmagicwoman.mybatistest.service.funtion.HandlerStringService;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        integers.sort(Integer::compareTo);//[1, 2, 3, 4, 5]
        integers.sort((o1, o2) -> o1.compareTo(o2));//[1, 2, 3, 4, 5]
        System.out.println(integers);
        List<String> strings = Arrays.asList("1", "2", "3", "4", "5");
        strings.sort((o1, o2) -> o2.compareTo(o1));//[5, 4, 3, 2, 1]
        System.out.println(strings);
    }
}
