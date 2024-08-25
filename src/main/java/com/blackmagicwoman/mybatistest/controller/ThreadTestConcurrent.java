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
//        t1.start();
//        t2.start();

        HashMap<MyKey, String> map = new HashMap<>();

        // 假设MyKey是一个自定义的类，其hashCode方法被重写为返回相同的值
        MyKey key1 = new MyKey("Key1");
        MyKey key2 = new MyKey("Key2");
        MyKey key3 = new MyKey("Key3");

        // 将这三个键放入HashMap中
        map.put(key1, "Value1");
        map.put(key2, "Value2");
        map.put(key3, "Value3");

        // 打印HashMap的内容以验证它们都在同一个槽中
        System.out.println("HashMap content: " + map);
    }

    // 自定义的键类，具有相同的hashCode
    static class MyKey {
        private final String value;

        public MyKey(String value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            // 这里故意返回一个常数，以模拟所有键哈希到同一个槽的情况
            return 1; // 返回一个固定的哈希码值
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            MyKey myKey = (MyKey) obj;
            return Objects.equals(value, myKey.value);
        }

        @Override
        public String toString() {
            return "MyKey{" + "value='" + value + '\'' + '}';
        }
    }
}
