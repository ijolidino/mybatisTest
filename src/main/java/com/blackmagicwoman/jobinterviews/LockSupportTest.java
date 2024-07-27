package com.blackmagicwoman.jobinterviews;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @program: mybatisTest
 * @description: 交替唤醒线程
 * @author: heise
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

    /**
     * 先unPark后park，则线程会跳过park步骤，直接执行后续的代码；
     * 如果先unPark后两次park，线程会阻塞；
     * @throws InterruptedException
     */
    @Test
    public void testPartAndUnPart() throws InterruptedException {
        MyThread myThread = new MyThread();
        myThread.start();
        Thread.currentThread().sleep(100);
        LockSupport.unpark(myThread);
        Thread.currentThread().sleep(1100);
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            System.out.println("线程"+Thread.currentThread().getName()+"开始进入方法");
            try {
                Thread.currentThread().sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            LockSupport.park();
            System.out.println("线程"+Thread.currentThread().getName()+"开始执行");
            LockSupport.unpark(Thread.currentThread());
            System.out.println("线程"+Thread.currentThread().getName()+"unPark完成");
        }
    }
}
