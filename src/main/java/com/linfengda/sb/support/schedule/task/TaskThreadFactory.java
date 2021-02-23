package com.linfengda.sb.support.schedule.task;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author linfengda
 * @date 2021-02-03 17:06
 */
public class TaskThreadFactory implements ThreadFactory {
    private final ThreadGroup group;
    private final String namePrefix;
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    public TaskThreadFactory(String threadName) {
        SecurityManager var1 = System.getSecurityManager();
        this.group = var1 != null ? var1.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.namePrefix = threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
