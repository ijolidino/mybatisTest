package com.blackmagicwoman.mybatistest.service;

/**
 * @program: mybatisTest
 * @description: 消息发送接口
 * @author: Fuwen
 * @create: 2022-04-26 07:15
 **/
public interface RabbitMQService {
    /**
     * 消息发送接口
     * @param msg
     * @return
     * @throws Exception
     */
    String sendMsg(String msg) throws Exception;
}
