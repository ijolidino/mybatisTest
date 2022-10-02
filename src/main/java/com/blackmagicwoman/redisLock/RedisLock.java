package com.blackmagicwoman.redisLock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description :Redis 公共分布式注解锁
 * prefix=“前缀”
 * value="spEL表达式" 例 #a.b 即获取参数对象a的成员变量b
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLock {


    /**
     * 添加前缀
     */
    String prefix() default "";

    /**
     * lock key
     */
    String value();

    /**
     * 锁超时时间，默认3分钟
     */
    long expireTime() default 3*60*1000;

    /**
     * 间隔时间，默认1000毫秒
     */
    long interval() default 1000;

    /**
     * 间隔请求次数
     */
    int loop() default 5;

}
