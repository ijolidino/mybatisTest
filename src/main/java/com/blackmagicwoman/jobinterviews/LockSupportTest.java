package com.blackmagicwoman.jobinterviews;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @program: mybatisTest
 * @description: 交替唤醒线程
 * @author: Fuwen
 * @create: 2023-05-07 20:35
 **/
@Slf4j
public class LockSupportTest {

    static Thread a = null;

    static Thread b = null;

    static boolean aa = true;

    static boolean bb = true;

    public static void main(String[] args) throws InterruptedException {
        char[] charArray = "1234567".toCharArray();
        char[] charArray1 = "abcdefg".toCharArray();

        a = new Thread(() -> {
            for (char c : charArray) {
                log.info("线程{}开始打印：" + c, Thread.currentThread().getName());
                LockSupport.unpark(b);
                LockSupport.park();
            }
            aa = false;
        }, "a1");

        b = new Thread(() -> {
            for (char c : charArray1) {
                log.info("线程{}开始打印：" + c, Thread.currentThread().getName());
                LockSupport.unpark(a);
                LockSupport.park();
            }
            bb = false;
        }, "b1");

        a.start();
        b.start();
//        while (aa|| bb){
//            //System.out.println("线程正在进行");
//        }
    }

    @Test
    public void test() {
        char[] charArray = "1234567".toCharArray();
        char[] charArray1 = "abcdefg".toCharArray();
        Object o = new Object();
        new Thread(() -> {
            synchronized (o) {
                for (char c : charArray1) {
                    System.out.print(c);
                    o.notifyAll();
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                o.notifyAll();
            }
        }).start();
        new Thread(() -> {
            synchronized (o) {
                for (char c : charArray) {

                    System.out.print(c);
                    o.notifyAll();
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                o.notifyAll();
            }
        }).start();
    }

}
