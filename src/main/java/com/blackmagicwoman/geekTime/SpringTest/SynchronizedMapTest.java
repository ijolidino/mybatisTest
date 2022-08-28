package com.blackmagicwoman.geekTime.SpringTest;

import com.blackmagicwoman.mybatistest.controller.ThreadTest;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: mybatisTest
 * @description: 线程安全的map,多线程操作原来的map是否会锁住
 * @author: Fuwen
 * @create: 2022-08-17 23:21
 **/
public class SynchronizedMapTest {

    public static void main(String[] args) throws IOException {
        int corePoolSize = 2;
        int maximumPoolSize = 4;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);
        ThreadFactory threadFactory = new SynchronizedMapTest.TestThreadFactory();
        RejectedExecutionHandler handler = new ThreadTest.MyIgnorePolicy();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue, threadFactory, handler);
        executor.prestartAllCoreThreads(); // 预启动所有核心线程
        HashMap<String, String> map = new HashMap<>();
        System.out.println("原来的map:"+map.hashCode());
        map.put("aa","aa");
        Map<String, String> stringStringMap = Collections.synchronizedMap(map);
        stringStringMap.put("11","11");
        System.out.println("后来的的map:"+map.hashCode());
        System.out.println("原来的stringStringMap:"+stringStringMap.hashCode());
        ConcurrentHashMap<String, String> map1 = new ConcurrentHashMap<>();
        for (int i = 1; i <= 10; i++) {
            SynchronizedMapTest.MyTask task = new SynchronizedMapTest.MyTask(map);
            executor.execute(task);
        }

        System.in.read(); //阻塞主线程
    }
    static class TestThreadFactory implements ThreadFactory{

        private final AtomicInteger mThreadNum = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "my-thread-" + mThreadNum.getAndIncrement());
            System.out.println(t.getName() + " has been created");
            return t;
        }
    }

    public static class MyIgnorePolicy implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            doLog(r, e);
        }

        private void doLog(Runnable r, ThreadPoolExecutor e) {
            // 可做日志记录等
            System.err.println( r.toString() + " rejected");
//          System.out.println("completedTaskCount: " + e.getCompletedTaskCount());
        }
    }

    static class MyTask implements Runnable {
        private Map<String,String> map;

        public MyTask(Map<String,String> map) {
            this.map = map;
        }

        @Override
        public void run() {
            try {
                int i=0;
                while (true){
                    map.put(i+++"",i+++"");
                    System.out.println("正在操作的线程为："+this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public String getmap() {
            return "";
        }

        @Override
        public String toString() {
            return "MyTask [name=" + map.hashCode()+ "]";
        }
    }
}
