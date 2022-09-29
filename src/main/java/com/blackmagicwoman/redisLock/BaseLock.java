package com.blackmagicwoman.redisLock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author :byLBD
 * @date :Created 2022/9/23
 * @description :Redis分布式基础锁，用于各业务模块扩展功能
 */
@Component
@Slf4j
public class BaseLock {

    @Autowired
    private RedisLockUtils redisLockUtils;

    /**
     * Redis 加锁
     * @param key 主键
     * @return
     */
    public boolean tryLock(String key){
        return redisLockUtils.tryLock(key);
    }
    /**
     * Redis 解锁
     * @param key 主键
     * @return
     */
    public boolean unLock(String key){
        return redisLockUtils.unLock(key);
    }


    /**
     * Redis
     * @param key 主键
     * @return
     */
    public boolean containKey(String key){
        return redisLockUtils.containKey(key);
    }


}
