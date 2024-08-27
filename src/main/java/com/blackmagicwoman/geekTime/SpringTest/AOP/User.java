package com.blackmagicwoman.geekTime.SpringTest.AOP;

/**
 * @program: mybatisTest
 * @description: 用户
 * @author: heise
 * @create: 2022-05-18 23:42
 **/
public class User{
    private String payNum;
    public User(String payNum) {
        this.payNum = payNum;
    }
    public String getPayNum() {
        return payNum;
    }
    public void setPayNum(String payNum) {
        this.payNum = payNum;
    }

    public void incr(){
        payNum+=1;
    }

}
