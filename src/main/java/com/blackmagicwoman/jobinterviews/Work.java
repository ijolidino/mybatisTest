package com.blackmagicwoman.jobinterviews;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @program: mybatisTest
 * @description: 一个任务失败，所有的任务都得失败
 * @author: heise
 * @create: 2023-05-07 11:54
 **/
@Slf4j
public class Work extends Thread{

    public ReentrantReadWriteLock readWriteLock;

    public static boolean isFail=false;

    private String threadName;

    private int count;

    private boolean isIsFailPr;
    public Work(String threadName,boolean isFail,int count,ReentrantReadWriteLock readWriteLock){
        this.threadName=threadName;
        this.readWriteLock=readWriteLock;
        this.count=count;
        isIsFailPr=isFail;
    }

    public static void main(String[] args) {
        ReentrantReadWriteLock readWriteLock1 = new ReentrantReadWriteLock();
        ArrayList<Work> works = new ArrayList<>();
        works.add(new Work("线程1",false,1,readWriteLock1));
        works.add(new Work("线程2",false,2,readWriteLock1));
        works.add(new Work("线程3",false,3,readWriteLock1));
        works.add(new Work("线程4",false,4,readWriteLock1));
        works.add(new Work("线程5",true,5,readWriteLock1));
        works.forEach(Work::start);
    }

    @Override
    public void run() {
        log.info("线程{}开始运行",threadName);
        int i=0;
        boolean canBreak=false;
        for (;;){
            if (canBreak){
                log.info("线程{}开始断开循环，结束线程{}",threadName,i);
                break;
            }
            log.info("线程{}开始进行作业{}",threadName,i);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Lock lock = readWriteLock.readLock();
            while (lock.tryLock()){
                log.info("线程{}获取读锁成功",threadName);
                if (isFail){
                    cancel();
                    canBreak=true;
                    break;
                }
                lock.unlock();
                log.info("线程{}获取读锁成功但是没有一个线程失败，继续执行",threadName);
                if (isIsFailPr){
                    break;
                }
            }
            if (isIsFailPr){
                Lock lock1 = this.readWriteLock.writeLock();
                lock1.lock();
                log.info("线程{}获取写锁成功，继续执行，线程名称为：{}",threadName,Thread.currentThread().getName());
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("线程{}获取写锁成功，线程失败,线程名称为:{}",threadName,Thread.currentThread().getName());
                isFail=isIsFailPr;
                lock1.unlock();
                log.info("线程{}获取写锁成功，解锁也成功",threadName);
                break;
            }
        }

    }

    public void cancel(){
        log.info("线程{}取消",threadName);
        log.info("线程{}开始补偿",threadName);
    }
}
