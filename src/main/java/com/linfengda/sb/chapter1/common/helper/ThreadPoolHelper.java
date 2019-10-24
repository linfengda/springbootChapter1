package com.linfengda.sb.chapter1.common.helper;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 描述: 获取spring线程池
 *
 * @author linfengda
 * @create 2019-02-19 10:44
 */
public class ThreadPoolHelper {
    public static ThreadPoolTaskExecutor initThreadPool(int corePoolSize, int maxPoolSize) {
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
