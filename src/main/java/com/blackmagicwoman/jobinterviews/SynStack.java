package com.blackmagicwoman.jobinterviews;

/**
 * @program: mybatisTest
 * @description: 同步线程代码
 * @author: heise
 * @create: 2023-05-08 21:09
 **/
class SynStack
{
    private char[] data = new char[6];
    private int cnt = 0; //表示数组有效元素的个数

    public synchronized void push(char ch)
    {
        if (cnt >= data.length)
        {
            try
            {
                System.out.println("生产线程"+Thread.currentThread().getName()+"准备休眠");
                this.wait();
                System.out.println("生产线程"+Thread.currentThread().getName()+"休眠结束了");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        this.notify();
        data[cnt] = ch;
        ++cnt;
        System.out.printf("生产线程"+Thread.currentThread().getName()+"正在生产第%d个产品，该产品是: %c\n", cnt, ch);
    }

    public synchronized char pop()
    {
        char ch;
        if (cnt <= 0)
        {
            try
            {
                System.out.println("消费线程"+Thread.currentThread().getName()+"准备休眠");
                System.out.println("消费线程"+Thread.currentThread().getName()+"准备休眠，此时的cnt为："+cnt);
                this.wait();
                System.out.println("消费线程"+Thread.currentThread().getName()+"休眠结束了");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        this.notify();
        System.out.println("消费线程"+Thread.currentThread().getName()+"休眠结束了"+"此时的cnt为："+cnt);
        ch = data[cnt-1];
        System.out.printf("消费线程"+Thread.currentThread().getName()+"正在消费第%d个产品，该产品是: %c\n", cnt, ch);
        --cnt;
        return ch;
    }
}

class Producer implements Runnable
{
    private SynStack ss = null;
    public Producer(SynStack ss)
    {
        this.ss = ss;
    }

    public void run()
    {
        char ch;
        for (int i=0; i<10; ++i)
        {
//            try{
//            Thread.sleep(100);
//            }
//            catch (Exception e){
//            }

            ch = (char)('a'+i);
            ss.push(ch);
        }
    }
}

class Consumer implements Runnable
{
    private SynStack ss = null;

    public Consumer(SynStack ss)
    {
        this.ss = ss;
    }

    public void run()
    {
        for (int i=0; i<10; ++i)
        {
            /*try{
            Thread.sleep(100);
            }
            catch (Exception e){
            }*/

            //System.out.printf("%c\n", ss.pop());
            ss.pop();
        }
    }
}


 class TestPC2
{
    public static void main(String[] args)
    {
        SynStack ss = new SynStack();
        Producer p = new Producer(ss);
        Consumer c = new Consumer(ss);


        Thread t1 = new Thread(p);
        t1.setName("1号");
        t1.start();
        /*Thread t2 = new Thread(p);
        t2.setName("2号");
        t2.start();*/

        Thread t6 = new Thread(c);
        t6.setName("6号");
        t6.start();
        Thread t7 = new Thread(c);
        t7.setName("7号");
        t7.start();
    }
}
