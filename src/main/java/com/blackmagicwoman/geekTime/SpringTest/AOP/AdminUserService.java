package com.blackmagicwoman.geekTime.SpringTest.AOP;

import org.springframework.stereotype.Service;

/**
 * @program: mybatisTest
 * @description: 用户登录
 * @author: Fuwen
 * @create: 2022-05-18 23:43
 **/
@Service
public class AdminUserService {
    public final User adminUser = new User("202101166");

    public void login() {
        System.out.println("admin user login...");
    }
}
