package com.blackmagicwoman.mybatistest.iterator;

import java.util.HashMap;

/**
 * @program: mybatisTest
 * @description: 自己实现迭代器
 * @author: Fuwen
 * @create: 2022-09-25 12:59
 **/
public class iteratorTest {
    //https://www.cnblogs.com/guoyansi19900907/archive/2020/01/01/12131001.html

    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap(16);
        map.put(7, "");
        map.put(11, "");
        map.put(43, "");
        map.put(59, "");
        map.put(19, "");
        map.put(3, "");
        map.put(35, "");
        map.put(71, "");
        map.put(111, "");
        map.put(431, "");
        map.put(591, "");
        map.put(191, "");
        map.put(31, "");
        map.put(351, "");
        map.put(72, "");
        map.put(112, "");
        map.put(432, "");
        map.put(592, "");
        map.put(192, "");
        map.put(32, "");
        map.put(352, "");

        System.out.println("遍历结果：");
        for (Integer key : map.keySet()) {
            System.out.print(key + " -> ");//192 -> 32 -> 352 -> 3 -> 35 -> 7 -> 71 -> 72 -> 11 -> 43 -> 111 -> 431 -> 591 -> 112 -> 432 -> 592 -> 19 -> 59 -> 191 -> 31 -> 351 ->
        }
        HashMap<Integer, String> integerStringHashMap = new HashMap<>(map);
        System.out.println("the second result of iterator:");
        integerStringHashMap.put(11111,"");
        integerStringHashMap.keySet().forEach(a-> System.out.print(a+"->"));//192->32->352->3->35->7->71->72->11->43->111->431->591->112->432->592->19->59->191->31->351->
    }
}
