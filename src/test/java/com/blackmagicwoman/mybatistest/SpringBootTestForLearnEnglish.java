package com.blackmagicwoman.mybatistest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @program: mybatisTest
 * @description: learning English
 * @author: Fuwen
 * @create: 2022-12-14 20:44
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringBootTestForLearnEnglish {
    @LocalServerPort
    private int port;

    @Resource
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldPassIfStringMatchs(){
        assertEquals("Hello World from Spring Boot",testRestTemplate.getForObject("http://localhost:"+port+"/",String.class));
    }
}
