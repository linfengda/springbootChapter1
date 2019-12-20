package com.linfengda.sb.chapter1.common.thread;

import lombok.Getter;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 描述: 线程池列表
 *
 * @author linfengda
 * @create 2019-05-28 22:20
 */
public enum ThreadPoolManager {
    /**
     * 异步线程池
     */
    MY_EXECUTOR_POOL("my-task-thread", "异步线程池", new ThreadPoolExecutor.DiscardPolicy()),
    /**
     * 分布式任务处理线程池
     */
    MY_DISTRIBUTE_TASK_POOL("my-distribute_task-thread", "分布式任务处理线程池", new ThreadPoolExecutor.CallerRunsPolicy()),
    ;

    @Getter
    private String prefix;
    @Getter
    private String desc;
    @Getter
    private RejectedExecutionHandler policy;

    ThreadPoolManager(String prefix, String desc, RejectedExecutionHandler policy) {
        this.prefix = prefix;
        this.desc = desc;
        this.policy = policy;
    }
}
