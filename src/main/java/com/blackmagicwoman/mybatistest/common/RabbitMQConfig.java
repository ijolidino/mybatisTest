package com.blackmagicwoman.mybatistest.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

/**
 * @program: 创建一个公共的配置类，定义一些配置信息RabbitMQ的队列主题名称
 * @description: 配置类
 * @author: heise
 * @create: 2022-04-25 07:47
 **/
@Slf4j
public class RabbitMQConfig {
    /**
     * RabbitMQ的队列主题名称
     */
    public static final String RABBITMQ_DEMO_TOPIC ="rabbitmqDemoToptc";
    /**
     * RabbitMQ的DIRECI交换机名称
     */
    public static final String RABBITMQ_DEMO_DIRECT_EXCHANGE ="rabbitmqDemoDirectExchange";

/**
 *     RabbttMQ的DIRECT交换机和队列绑定的匹配键 DtrectRouting
 */
    public static final String RABBITMQ_DEMO_DIRECT_ROUTING = "rabbitmqDemoDtrectRouting";

    @Test
    public void testPatten(){
        int i=11;
        String quote = Pattern.quote("");
        log.info(quote);
        String s="aaa";
        String s1="bbb";
        System.out.format("aaa为：%s，bbb为：%s",s,s1);
    }
}
