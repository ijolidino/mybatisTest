package com.blackmagicwoman.geekTime.SpringTest.AOP;

/**
 * @program: mybatisTest
 * @description: 充电类
 * @author: heise
 * @create: 2022-05-18 23:43
 **/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ElectricService {
    @Autowired
    private AdminUserService adminUserService;
    public void charge() throws Exception {
        System.out.println("Electric charging ...");
        this.pay();
    }

    public void pay() throws Exception {
        adminUserService.login();
        String payNum = adminUserService.adminUser.getPayNum();
        System.out.println("User pay num : " + payNum);
        System.out.println("Pay with alipay ...");
        Thread.sleep(1000);
    }
}
