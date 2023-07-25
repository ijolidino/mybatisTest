package com.blackmagicwoman.mybatistest.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: mybatisTest
 * @description: 构造异步线程测试
 * @author: Fuwen
 * @create: 2023-03-30 20:09
 **/
@Configuration
@EnableAsync
public class ThreadPoolConfig {
    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE=9;
    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE=50;
    /**
     * 队列最大长度
     */
    private static final int QUEUE_CAPACITY=500;
    /**
     * 线程池维护线程所允许的空闲时间
     */
    private static final int KEEP_ALIVE_SECONDS=60;
    /**
     * 线程名称前缀
     */
    private static final String THREAD_NAME_PREFIX="async_thread_";
    @Bean(name = "asyncThreadExecutor")
    public ThreadPoolTaskExecutor getThread(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //等待所有任务结束再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
