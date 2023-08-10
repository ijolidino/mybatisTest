package com.blackmagicwoman.java8.concurrent;

/**
 * @program: mybatisTest
 * @description: 序列号生成器
 * @author: Fuwen
 * @create: 2023-08-08 22:08
 **/
public class SerialNumbers {

    private volatile int serialNumber = 0;
    public int nextSerialNumber() {
        return serialNumber++; // Not thread-safe
    }
}
