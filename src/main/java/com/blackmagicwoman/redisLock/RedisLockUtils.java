package com.blackmagicwoman.redisLock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author :byLBD
 * @date :Created 2022/9/02 10:25
 * @description :Redis分布式锁
 */
@Component
@Slf4j
public class RedisLockUtils {

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * Redis 编号加分布式加锁 默认循环5次，每次间隔1秒；加锁生效时间为3分钟
     * @param Nokey 编号
     * @return
     */
    public boolean tryLock(String Nokey){
        int loop = 5;//循环5次
        return tryLock(Nokey,loop);
    }

    /**
     * Redis 编号加分布式加锁
     * 默认每次循环间隔1秒钟，
     * 加锁生效时间为1分钟
     * @param key 编号
     * @param loop 循环次数
     * @return
     */
    public boolean tryLock(String key,int loop){
        long interval = 1000;//默认间隔1秒
        return tryLock(key,loop,interval);
    }

    /**
     * Redis 编号加分布式加锁
     * 默认过期时间3分钟
     * @param key 编号
     * @param loop 循环次数
     * @param interval 间隔时间
     * @return
     */
    public boolean tryLock(String key,int loop,long interval){
        return tryLock(key,loop,interval,0);
    }

    /**
     * Redis 编号加分布式加锁
     * @param key 编号
     * @param loop 循环次数
     * @param interval 间隔时间
     * @param timeout 有效时间
     * @return
     */
    public boolean tryLock(String key,int loop,long interval,long timeout) {
        if(loop<=0){
            loop=1;
        }
        for(int i=1;i<=loop;i++) {//重试3次
            boolean isLock =this.tryLock(key,timeout);
            if(isLock){
                return true;
            }
            try{
                Thread.sleep(interval);//睡眠5秒重试
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }



    /**
     * Redis 编号加分布式加锁 默认三秒钟
     * @param key 编号
     * @return
     */
    public boolean tryLock(String key,long times) {
        return tryLock(key,key,times);
    }


    /**
     * Redis 分布式加锁 过期时间小于0时，默认3分钟
     * 设定值 如果已经存在，则返回false,成功返回true
     * @return
     */
    public boolean tryLock(String key, String value, long timeout) {
        if (timeout <= 0) {
            timeout = 60 * 3 * 1000;
        }
        return redisTemplate.opsForValue().setIfAbsent(key, value,timeout,TimeUnit.MILLISECONDS);
    }

    /**
     * Redis 分布式解锁
     *
     * @param key
     */
    public boolean unLock(String key) {
        Object object = redisTemplate.opsForValue().get(key);
        if (Objects.nonNull(object)) {
            return redisTemplate.opsForValue().getOperations().delete(key);
        }
        return false;
    }


    /**
     * Redis 是否包锁
     * @param key 锁 默认五次，每次间隔1秒钟
     * @return
     */
    public boolean containKey(String key) {
        Object object = redisTemplate.opsForValue().get(key);
        if (Objects.nonNull(object)) {
            return true;
        }
        return false;
    }

}
