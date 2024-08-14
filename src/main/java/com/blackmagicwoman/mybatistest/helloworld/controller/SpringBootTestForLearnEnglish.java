package com.blackmagicwoman.mybatistest.helloworld.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: mybatisTest
 * @description:
 * @author: heise
 * @create: 2022-12-14 20:51
 **/
@RestController
public class SpringBootTestForLearnEnglish {

    @RequestMapping
    public String helloWorld(){
        return "Hello World from Spring Boot";
    }
}
