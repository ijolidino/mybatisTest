package com.blackmagicwoman.geekTime.SpringTest.AOP;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

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
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>(32);
        map.put("a","a");
        map.put("b","b");
        String a="10";
        strings.add(a);
        List<List<String>> arrayLists = new ArrayList<>();
        arrayLists.add(strings);
        Integer b=1;
        Integer c=2;
        Long d=3L;
        Integer bb=127;
        Long cc=127L;
        Long dd=254L;
        Long ee=254L;
        long ff= 3L;
        System.out.println(d==(b+c));
        System.out.println(d.equals(b+c));
        System.out.println(dd==(bb+cc));
        System.out.println(dd.equals(ee));
    }
    static class ThreadLocalExample implements Runnable{

        // SimpleDateFormat 不是线程安全的，所以每个线程都要有自己独立的副本
        private static final ThreadLocal<SimpleDateFormat> formatter = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd HHmm"));

        public static void main(String[] args) throws InterruptedException {
            ThreadLocalExample obj = new ThreadLocalExample();
            for(int i=0 ; i<10; i++){
                Thread t = new Thread(obj, ""+i);
                Thread.sleep(new Random().nextInt(1000));
                t.start();
            }
        }

        @Override
        public void run() {
            System.out.println("Thread Name= "+Thread.currentThread().getName()+" default Formatter = "+formatter.get().toPattern());
            try {
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            formatter.remove();
            //formatter pattern is changed here by thread, but it won't reflect to other threads
            formatter.set(new SimpleDateFormat());

            System.out.println("Thread Name= "+Thread.currentThread().getName()+" formatter = "+formatter.get().toPattern());
        }
    }
}
