package com.blackmagicwoman.mybatistest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class MybatisTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisTestApplication.class, args);
    }

}
