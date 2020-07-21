package com.linfengda.sb.support.cache.util;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 描述: 获取spring线程池
 *
 * @author linfengda
 * @create 2019-02-19 10:44
 */
public class ThreadPoolHelper {
    /**
     * 获取spring线程池
     * @param corePoolSize      核心线程数
     * @param maxPoolSize       最大线程数
     * @param threadNamePrefix  线程分类
     * @return
     */
    public static ThreadPoolTaskExecutor initThreadPool(int corePoolSize, int maxPoolSize, String threadNamePrefix) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(30);
        executor.setKeepAliveSeconds(60);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.setThreadNamePrefix("my-task-thread");
        executor.afterPropertiesSet();
        return executor;
    }

}
