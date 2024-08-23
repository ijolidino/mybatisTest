package com.blackmagicwoman.mybatistest.controller;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @program: mybatisTest
 * @description: 并发问题，作为判断线程运行的先后顺序
 * @author: heise
 * @create: 2022-11-19 22:48
 **/
public class ThreadTestConcurrent {

    static AtomicStampedReference atsr=new AtomicStampedReference(1,100);

    static Thread t1=new Thread(() -> {
        int stamp = atsr.getStamp();
        boolean b = atsr.compareAndSet(1, 2, stamp, 200);
        System.out.println("t1线程是否成功设值："+b+"，值为："+2);
        int stamp1 = atsr.getStamp();
        boolean b1 = atsr.compareAndSet(2, 1, stamp1, 300);
        System.out.println("t1线程是否成功设值："+b1+"，值为："+1);
    });

    static Thread t2=new Thread(() -> {
        int stamp = atsr.getStamp();
        try {
            TimeUnit.NANOSECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        boolean b = atsr.compareAndSet(1, 4, stamp, 400);
        System.out.println("t2线程是否成功设值："+b+"，值为："+4);
    });

    public static void main(String[] args) {
        t1.start();
        t2.start();
    }
}
