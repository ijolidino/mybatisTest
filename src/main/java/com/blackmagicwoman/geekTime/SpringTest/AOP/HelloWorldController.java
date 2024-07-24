package com.blackmagicwoman.geekTime.SpringTest.AOP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: mybatisTest
 * @description: 发送接口
 * @author: heise
 * @create: 2022-05-18 23:46
 **/

@RestController
public class HelloWorldController {
    @Autowired
    ElectricService electricService;
    @RequestMapping(path = "charge", method = RequestMethod.GET)
    public void charge() throws Exception{
        electricService.charge();
    };
}
