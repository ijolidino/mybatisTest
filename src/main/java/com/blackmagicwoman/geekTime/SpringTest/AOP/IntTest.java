package com.blackmagicwoman.geekTime.SpringTest.AOP;

/**
 * @program: mybatisTest
 * @description: 测试克隆复制
 * @author: Fuwen
 * @create: 2022-08-13 15:16
 **/
public class IntTest implements Cloneable{

    private int i;

    private IntTest intTest;

    private char c;
    public IntTest(int i){
        this.i=i;
    }

    public IntTest(int i, char c) {
        this.c=c;
        this.i = i;
        if (--i>0){
            this.intTest=new IntTest(i,(char)(c+1));
        }
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void incr(){
        i=i+1;
        System.out.println(i);
        sout();
    }

    public void increment(){
        c++;
        if (intTest!=null){
            intTest.increment();
        }
    }
    private void sout() {
        System.out.println(i);
    }

    @Override
    public IntTest clone() {

        try {
            IntTest clone = (IntTest) super.clone();
            if (intTest!=null){
                this.intTest=intTest.clone();
            }
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        String s=":"+c;
        if (intTest!=null){
            s+=intTest.toString();
        }
        return s;
    }
}
