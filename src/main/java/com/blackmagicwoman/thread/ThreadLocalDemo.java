package com.blackmagicwoman.thread;

import com.blackmagicwoman.geekTime.SpringTest.AOP.User;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * {@code @program:} mybatisTest
 * {@code @description:} 多线程map样例
 * {@code @author:} heise
 * {@code @create:} 2023-03-09 22:02
 **/
@Slf4j
public class ThreadLocalDemo {

    public static <D,S extends D> void copy(DynamicArray<D> dest,
                                            DynamicArray<S> src){
        for(int i=0; i<src.size(); i++){
            dest.add(src.get(i));
        }
    }

    static class UserChild extends User{

        public UserChild(String payNum) {
            super(payNum);
        }
    }

    static class UserChild2 extends User{

        public UserChild2(String payNum) {
            super(payNum);
        }
    }
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        DynamicArray<UserChild> array = new DynamicArray<>();
        DynamicArray<User> array1 = new DynamicArray<>();
        DynamicArray<UserChild2> array2 = new DynamicArray<>();
        array1.add(new User("1"));
        array.add(new UserChild("1"));
        array2.add(new UserChild2("1"));
        copy(array1,array);
        copy(array1,array2);
        log.info(array1.toString());
        array1.get(2).setPayNum("2");
        log.info(array1.toString());
        new Thread(()->new ThreadLocal<>().set("aaa"));
        new Thread(()->new ThreadLocal<>().set("bbb"));
        Thread t = new Thread(()->test("abc",false));
        t.start();
        t.join();
        //System.out.println("--gc后--");
        Thread t2 = new Thread(() -> test("def", true));
        t2.start();
        t2.join();
        System.out.println("-----------");
    }

    private static void test(String s,boolean isGC)  {
        try {
            ThreadLocal<Object> t1 = new ThreadLocal<>();
            t1.set(s);
            ThreadLocal<Object> t2 = new ThreadLocal<>();
            t2.set(s+s);
            ThreadLocal<Object> t3 = new ThreadLocal<>();
            t3.set(s+s+s);
            ThreadLocal<Object> t4 = new ThreadLocal<>();
            t4.set(s+s+s);
             new ThreadLocal<>().set(s+s+s+s);
            Object o1 = t1.get();
            System.out.println("----------");
            //Thread.sleep(30000);
            if (isGC) {
                System.gc();
            }

            Thread t = Thread.currentThread();
            Class<? extends Thread> clz = t.getClass();
            Field field = clz.getDeclaredField("threadLocals");
            field.setAccessible(true);
            Object ThreadLocalMap = field.get(t);
            Class<?> tlmClass = ThreadLocalMap.getClass();
            Field tableField = tlmClass.getDeclaredField("table");
            tableField.setAccessible(true);
            Object[] arr = (Object[]) tableField.get(ThreadLocalMap);
            for (Object o : arr) {
                if (o != null) {
                    Class<?> entryClass = o.getClass();
                    Field valueField = entryClass.getDeclaredField("value");
                    Field referenceField = entryClass.getSuperclass().getSuperclass().getDeclaredField("referent");
                    valueField.setAccessible(true);
                    referenceField.setAccessible(true);
                    System.out.println(String.format("弱引用key:%s,值:%s", referenceField.get(o), valueField.get(o)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

