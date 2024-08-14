package com.blackmagicwoman.mybatistest.consumer;

import com.blackmagicwoman.mybatistest.common.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: mybatisTest
 * @description: 消费者
 * @author: heise
 * @create: 2022-04-26 07:22
 **/
@Component
@RabbitListener(queues = {RabbitMQConfig.RABBITMQ_DEMO_TOPIC})
public class RabbitMQConsumer {

    @RabbitHandler
    public void process(Map map){
        System.out.println("消费者获取到消息"+map.toString());
    }
}
