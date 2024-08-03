package com.blackmagicwoman.mybatistest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.blackmagicwoman"})
@EnableAspectJAutoProxy
@MapperScan({"com.blackmagicwoman.mybatistest.mapper"})
@EnableTransactionManagement
public class MybatisTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisTestApplication.class, args);
    }

}
