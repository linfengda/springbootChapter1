package com.linfengda.sb.chapter1.common.config;

import com.linfengda.sb.chapter1.common.exception.handler.MyAsyncUncaughtExceptionHandler;
import com.linfengda.sb.chapter1.common.exception.handler.MySchedulingExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;

/**
 * 描述: 定时器线程池配置
 *
 * @author linfengda
 * @create 2018-11-20 10:18
 */
@SpringBootConfiguration
@EnableAsync
public class MySchedulingConfig implements SchedulingConfigurer, AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        return null;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new MyAsyncUncaughtExceptionHandler();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setTaskScheduler(taskScheduler());
    }

    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        // 将@Scheduled配置的定时任务，分发给2个线程并行执行，超过2个任务需要并行执行不考虑
        scheduler.setPoolSize(2);
        scheduler.setErrorHandler(new MySchedulingExceptionHandler());
        scheduler.setThreadNamePrefix("my-scheduler-thread");
        return scheduler;
    }

}
