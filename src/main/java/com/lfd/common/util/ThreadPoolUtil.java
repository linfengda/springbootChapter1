package com.lfd.common.util;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 描述: 获取spring线程池
 *
 * @author linfengda
 * @create 2019-02-19 10:44
 */
public class ThreadPoolUtil {

    /**
     * 获取spring线程池
     * @param corePoolSize              核心线程数
     * @param maxPoolSize               最大线程数
     * @param queueCapacity             等待队列长度
     * @param keepAliveSeconds          存活时间
     * @param threadNamePrefix          名称
     * @param rejectedExecutionHandler  策略
     * @return
     */
    public static ThreadPoolTaskExecutor initThreadPool(int corePoolSize, int maxPoolSize, int queueCapacity, int keepAliveSeconds, String threadNamePrefix, RejectedExecutionHandler rejectedExecutionHandler) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.afterPropertiesSet();
        return executor;
    }

}
