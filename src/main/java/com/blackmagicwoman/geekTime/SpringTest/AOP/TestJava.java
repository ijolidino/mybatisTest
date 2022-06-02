package com.blackmagicwoman.geekTime.SpringTest.AOP;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: mybatisTest
 * @description:
 * @author: Fuwen
 * @create: 2022-05-24 22:03
 **/
public class TestJava {
    @Test
    public void test(){
        List<String> strings = new ArrayList<>();
        String a="10";
        strings.add(a);
        List<List<String>> arrayLists = new ArrayList<>();
        arrayLists.add(strings);
    }
}
