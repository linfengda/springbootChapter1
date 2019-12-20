package com.linfengda.sb.chapter1.common.config;

import com.linfengda.sb.chapter1.common.thread.ThreadPoolManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 描述: 线程池配置
 *
 * @author linfengda
 * @create 2019-01-10 17:00
 */
@SpringBootConfiguration
@EnableAsync
public class MyExecutorPoolConfig {

    @Value("${thread.pool.corePoolSize}")
    private int corePoolSize;
    @Value("${thread.pool.maxPoolSize}")
    private int maxPoolSize;
    @Value("${thread.pool.queueSize}")
    private int queueSize;
    @Value("${thread.pool.keepAlive}")
    private int keepAlive;

    @Bean("myExecutorPool")
    public Executor myExecutorPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueSize);
        executor.setKeepAliveSeconds(keepAlive);
        executor.setRejectedExecutionHandler(ThreadPoolManager.MY_EXECUTOR_POOL.getPolicy());
        executor.setThreadNamePrefix(ThreadPoolManager.MY_EXECUTOR_POOL.getPrefix());
        executor.initialize();
        return executor;
    }

    @Bean("myCallerRunsPool")
    public Executor myCallerRunsPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueSize);
        executor.setKeepAliveSeconds(keepAlive);
        executor.setRejectedExecutionHandler(ThreadPoolManager.MY_DISTRIBUTE_TASK_POOL.getPolicy());
        executor.setThreadNamePrefix(ThreadPoolManager.MY_DISTRIBUTE_TASK_POOL.getPrefix());
        executor.initialize();
        return executor;
    }
}
