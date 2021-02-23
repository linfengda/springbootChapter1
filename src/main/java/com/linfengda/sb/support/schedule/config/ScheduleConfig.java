package com.linfengda.sb.support.schedule.config;

import com.linfengda.sb.support.schedule.scanner.ExtendsBeanRegister;
import com.linfengda.sb.support.schedule.task.ScheduleExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author linfengda
 * @date 2021-02-03 16:19
 */
public class ScheduleConfig {

    @Bean
    public ExtendsBeanRegister extendsBeanRegister() {
        return new ExtendsBeanRegister();
    }

    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        // 将@Scheduled配置的定时任务，分发给10个线程并行执行，超过10个任务需要并行执行不考虑
        scheduler.setPoolSize(10);
        scheduler.setErrorHandler(new ScheduleExceptionHandler());
        scheduler.setThreadNamePrefix("schedule-task-thread");
        return scheduler;
    }
}
