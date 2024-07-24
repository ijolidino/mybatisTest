package com.blackmagicwoman.mybatistest;

import com.blackmagicwoman.mybatistest.service.funtion.HandlerStringService;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @program: mybatisTest
 * @description: 参数化函数的测试
 * @author: heise
 * @create: 2022-09-14 21:48
 **/
public class FuntionalTest {
    public static void main(String[] args) {
        HandlerStringService handlerStringService = new HandlerStringService();
        String a = "1234567890";
        String s1 = handlerStringService.handlerString(a, (s) -> s + "0987654321");
        System.out.println(s1);
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        integers.sort(Integer::compareTo);//[1, 2, 3, 4, 5]
        integers.sort((o1, o2) -> o1.compareTo(o2));//[1, 2, 3, 4, 5]
        System.out.println(integers);
        List<String> strings = Arrays.asList("1", "2", "3", "4", "5");
        strings.sort((o1, o2) -> o2.compareTo(o1));//[5, 4, 3, 2, 1]
        System.out.println(strings);
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(5, 4);
        List<Integer> numbers3 = Arrays.asList(10, 11, 12, 13);
        List<int[]> pairs =
                numbers1.stream()
                        .flatMap(i -> numbers2.stream()
                                .map(j -> new int[]{i, j})
                        )
                        .collect(Collectors.toList());
        System.out.println(pairs);
        List<Integer> collect = numbers2.stream().flatMap(i -> numbers1.stream()
                .filter(j -> i % j == 0)).collect(Collectors.toList());
        System.out.println(collect);//[1, 1, 2]
        List<List<Integer>> collect1 = numbers1.stream()
                .flatMap(i ->
                        numbers2.stream()
                                .filter(j -> (i + j) % 3 == 0)
                                .map(j -> Arrays.asList(i, j))
                )
                .collect(Collectors.toList());
        System.out.println(collect1);//[[1, 5], [2, 4]]
        List<Integer> collect2 = numbers2.stream().flatMap(i -> numbers3.stream().filter(j -> j * i > 55)).distinct().collect(Collectors.toList());
        System.out.println(collect2);
        ifIsPresentTest();
    }

    private static void ifIsPresentTest() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5,5);
        integers.stream().filter(i -> i == 5).findAny().ifPresent(FuntionalTest::testSprint);
        Optional<Integer> max = integers.stream().max(Comparator.comparingInt(Integer::intValue));
        Integer integer = max.orElse(1);
        System.out.println("最大值"+integer);
        int[] ints = IntStream.range(1, 100).toArray();
    }

    private static void testSprint(Object objects) {
        System.out.println(objects);
    }


}
