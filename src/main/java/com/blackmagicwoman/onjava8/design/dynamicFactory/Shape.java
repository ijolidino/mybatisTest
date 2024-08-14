package com.blackmagicwoman.onjava8.design.dynamicFactory;

/**
 * @program: mybatisTest
 * @description: 形状
 * @author: heise
 * @create: 2022-11-13 09:58
 **/
public class Shape {
    private static int counter = 0;
    private int id = counter++;
    @Override public String toString() {
        return
                getClass().getSimpleName() + "[" + id + "]";
    }
    public void draw() {
        System.out.println(this + " draw");
    }
    public void erase() {
        System.out.println(this + " erase");
    }
}
