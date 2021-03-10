package com.lfd.soa.srv.demo.support.schedule.task.bean;

import com.lfd.soa.srv.demo.support.redis.lock.RedisDistributedLock;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.sql.SQLException;
import java.util.concurrent.ScheduledFuture;

/**
 * 任务执行
 *
 * @author linfengda
 * @date 2021-02-03 16:53
 */
@Slf4j
@Data
public class JobTaskFuture {
    /**
     * 任务执行的最小单元
     */
    private JobCell jobCell;
    /**
     * 任务调度器
     */
    private ScheduledFuture scheduledFuture;
    /**
     * 任务调度线程池
     */
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    /**
     * 分布式锁
     */
    private RedisDistributedLock redisDistributedLock;
    /**
     * 任务状态 true:启动、false：未启动
     */
    private boolean state;
    /**
     * 任务时间表达式
     */
    private String cron;


    /**
     * 构造器
     * @param jobCell                   任务执行最小单元
     * @param threadPoolTaskScheduler   定时任务执行器
     * @param redisDistributedLock      分布式锁
     */
    public JobTaskFuture(JobCell jobCell, ThreadPoolTaskScheduler threadPoolTaskScheduler, RedisDistributedLock redisDistributedLock) {
        this.jobCell = jobCell;
        this.cron = jobCell.getCron();
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        this.redisDistributedLock = redisDistributedLock;
    }

    /**
     * 启动定时任务
     *
     * @return
     */
    public boolean start() {
        if (this.state) {
            return true;
        }
        this.scheduledFuture = this.threadPoolTaskScheduler.schedule(this::doExecute, new JobTrigger(this.cron));
        this.state = true;
        return true;
    }

    /**
     * 执行任务
     */
    public void doExecute() {
        if (!jobCell.isDistribute() && !redisDistributedLock.tryLock(jobCell.getName())) {
            return;
        }
        long startTime = System.currentTimeMillis();
        try {
            jobCell.run();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            log.info("定时任务[{}]执行完成，耗时：{}ms", jobCell.getName(), System.currentTimeMillis() - startTime);
        }
    }

    /**
     * 停止定时任务
     *
     * @return
     */
    public boolean stop() {
        if (!this.state) {
            return true;
        }
        if (this.scheduledFuture != null) {
            boolean b = this.scheduledFuture.cancel(Boolean.TRUE);
            if (b) {
                this.state = false;
            }
            return b;
        }
        this.state = false;
        return true;
    }

    /**
     * 重启定时任务
     *
     * @return
     */
    public boolean reStart() {
        if (this.stop()) {
            return this.start();
        }
        return true;
    }

}
