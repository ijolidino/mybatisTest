package com.blackmagicwoman.jobinterviews.algorithm.philosopherquestion;

import lombok.extern.slf4j.Slf4j;

/**
 * @program: mybatisTest
 * @description: 哲学家问题
 * @author: heise
 * @create: 2023-05-07 10:43
 **/
@Slf4j
public class Philosopher extends Thread {

    private String name;

    private int count;

    private ChopStick chopStickLeft;

    private ChopStick chopStickRight;

    public Philosopher(String name, int count, ChopStick chopStickLeft, ChopStick chopStickRight) {
        this.name = name;
        this.count = count;
        this.chopStickLeft = chopStickLeft;
        this.chopStickRight = chopStickRight;
    }

    public static void main(String[] args) {
        ChopStick chopStick0 = new ChopStick();
        ChopStick chopStick1 = new ChopStick();
        ChopStick chopStick2 = new ChopStick();
        ChopStick chopStick3 = new ChopStick();
        ChopStick chopStick4 = new ChopStick();
        Philosopher philosopher0 = new Philosopher("0", 0, chopStick0, chopStick1);
        Philosopher philosopher1 = new Philosopher("1", 1, chopStick1, chopStick2);
        Philosopher philosopher2 = new Philosopher("2", 2, chopStick2, chopStick3);
        Philosopher philosopher3 = new Philosopher("3", 3, chopStick3, chopStick4);
        Philosopher philosopher4 = new Philosopher("4", 4, chopStick4, chopStick0);
        philosopher0.start();
        philosopher1.start();
        philosopher2.start();
        philosopher3.start();
        philosopher4.start();

    }

    /**
     * 打破每个人都拿左边的习惯，让他们偶数先拿左边，奇数先拿右边
     */
    @Override
    public void run() {
        log.info("第{}个科学家{}开始进入拿筷子",count,name);
        if (count/2==0){
            sync(chopStickLeft,chopStickRight);
        }else {
            sync(chopStickRight,chopStickLeft);
        }
    }

    private void sync(ChopStick first,ChopStick second) {
        synchronized (first) {
            log.info("第{}个科学家{}已经拿到左筷子",count,name);
            try {
                Thread.sleep(10000);
                synchronized (second) {
                    log.info("第{}个科学家{}已经拿到右筷子",count,name);
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + "这个哲学家吃完了！！" + name + "第" + count + "位");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class ChopStick {

    }
}
