package com.blackmagicwoman.geekTime.SpringTest.AOP;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;

/**
 * @program: mybatisTest
 * @description: 代理类
 * @author: Fuwen
 * @create: 2022-05-18 23:44
 **/

@Aspect
@Service
@Slf4j
public class AopConfig {
    @Before("execution(* com.blackmagicwoman.geekTime.SpringTest.AOP.AdminUserService.login(..)) ")
    public void logAdminLogin(JoinPoint pjp) throws Throwable {
        System.out.println("! admin login ...");
    }
}
