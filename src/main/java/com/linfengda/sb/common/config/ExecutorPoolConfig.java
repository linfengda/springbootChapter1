package com.linfengda.sb.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 描述: 线程池配置
 *
 * @author linfengda
 * @create 2019-01-10 17:00
 */
@Configuration
@EnableAsync
public class ExecutorPoolConfig {

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
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.setThreadNamePrefix("my-task-thread");
        executor.initialize();
        return executor;
    }
}
