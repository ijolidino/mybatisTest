package com.blackmagicwoman.java8.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @program: mybatisTest
 * @description: 序列号检测器
 * @author: Fuwen
 * @create: 2023-08-08 22:07
 **/
public class SerialNumberChecker implements Runnable{

    private CircularSet serials = new CircularSet(1000);
    private SerialNumbers producer;

    public SerialNumberChecker(SerialNumbers producer) {
        this.producer = producer;
    }
    @Override public void run() {
        while(true) {
            int serial = producer.nextSerialNumber();
            if(serials.contains(serial)) {
                System.out.println("Duplicate: " + serial);
                System.exit(0);
            }
            serials.add(serial);
        }
    }
    static void test(SerialNumbers producer) {
        SerialNumberChecker serialNumberChecker = new SerialNumberChecker(producer);
        SerialNumberChecker serialNumberChecker1 = new SerialNumberChecker(producer);
        SerialNumberChecker serialNumberChecker2 = new SerialNumberChecker(producer);
        System.out.printf(serialNumberChecker2.toString()+serialNumberChecker1.toString()+serialNumberChecker.toString());
        for(int i = 0; i < 10; i++)
            CompletableFuture.runAsync(
                    new SerialNumberChecker(producer));
        new Nap(4, "No duplicates detected");
    }

    static class Nap{
        public Nap(double t) { // Seconds
            try {
                TimeUnit.MILLISECONDS.sleep((int)(1000 * t));
            } catch(InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        public Nap(double t, String msg) {
            this(t);
            System.out.println(msg);
        }
    }
}
