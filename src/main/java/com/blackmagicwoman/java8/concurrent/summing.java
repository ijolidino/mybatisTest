package com.blackmagicwoman.java8.concurrent;

import java.util.Timer;
import java.util.function.LongSupplier;
import java.util.stream.LongStream;

/**
 * @program: mybatisTest
 * @description: 并行流式计算
 * @author: Fuwen
 * @create: 2022-09-04 17:47
 **/
public class summing {

    static void countTime(String id, long trueValue, LongSupplier longSupplier){
        System.out.print(id+":");
        long l = System.currentTimeMillis();
        long asLong = longSupplier.getAsLong();
        long l1 = System.currentTimeMillis();
        if (asLong==trueValue){
            System.out.println(l1-l+"ms");
        }else {
            System.out.println("error");
        }
    }

    public static final int count=1_000_000_000;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        long countSum=(long)count*((long) count+1)/2;
        long end = System.currentTimeMillis();
        System.out.println("countSum:"+(start-end)+"ms");
        countTime("简单流式0到count的简单求和",countSum,()-> LongStream.rangeClosed(0,count).sum());
        countTime("并行流式0到count的简单求和",countSum,()->LongStream.rangeClosed(0,count).parallel().sum());
        countTime("迭代器迭代0到count的求和",countSum,()->LongStream.iterate(0,i->i+1).limit(count+1).sum());
    }
}
