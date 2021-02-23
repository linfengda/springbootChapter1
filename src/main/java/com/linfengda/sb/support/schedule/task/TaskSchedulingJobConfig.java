package com.linfengda.sb.support.schedule.task;

import com.linfengda.sb.support.redis.lock.RedisDistributedLock;
import com.linfengda.sb.support.schedule.scanner.JobMappingScanner;
import com.linfengda.sb.support.schedule.task.bean.JobCell;
import com.linfengda.sb.support.schedule.task.bean.JobPublishMsg;
import com.linfengda.sb.support.schedule.task.bean.JobState;
import com.linfengda.sb.support.schedule.task.bean.JobTaskFuture;
import com.linfengda.sb.support.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务处理中心：初始化、启动、停止等
 *
 * @author linfengda
 * @date 2021-02-03 16:41
 */
@Slf4j
public class TaskSchedulingJobConfig {
    /**
     * 所有定时任务
     */
    private static final Map<String, JobTaskFuture> TASK_MAP = new HashMap<>(32);
    /**
     * 定时任务执行线程池
     */
    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(2, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new TaskThreadFactory("schedule-ctrl-task-thread"));


    private TaskSchedulingJobConfig() {}

    /**
     * 初始化定时任务
     */
    public static void initTask() {
        try {
            log.info("初始化缓存所有定时任务......");
            // 初始化扫描定时任务
            JobMappingScanner jobMappingScanner = new JobMappingScanner();
            Map<String, JobCell> jobCellMap = jobMappingScanner.scanJob();
            for (JobCell jobCell : jobCellMap.values()) {
                JobTaskFuture jobTaskFuture = new JobTaskFuture(jobCell, SpringUtil.getBean(ThreadPoolTaskScheduler.class), SpringUtil.getBean(RedisDistributedLock.class));
                TASK_MAP.put(jobCell.getName(), jobTaskFuture);
                jobTaskFuture.start();
            }
            log.info("初始化缓存所有定时任务成功！");
        } catch (Exception e) {
            log.error("初始化缓存所有定时任务失败！", e);
        }
    }

    /**
     * 变更定时任务执行周期、广播到其他服务器
     *
     * @param jobKey    任务key
     * @param cron      新表达式
     */
    public static void updateJobCron(String jobKey, String cron) {
        JobPublishMsg jobPublishMsg = new JobPublishMsg();
        jobPublishMsg.setJobKey(jobKey);
        jobPublishMsg.setCron(cron);
        publish(jobPublishMsg);
    }

    /**
     * 监听当变更定时任务执行周期时
     *
     * @param jobKey    任务key
     * @param cron      新表达式
     */
    public static void onChangeJobCron(String jobKey, String cron) {
        JobTaskFuture jobTaskFuture = TASK_MAP.get(jobKey);
        jobTaskFuture.setCron(cron);
        if (jobTaskFuture.isState()) {
            jobTaskFuture.reStart();
        }
    }

    public static void stopTask(String jobKey) {
        JobPublishMsg jobPublishMsg = new JobPublishMsg();
        jobPublishMsg.setJobKey(jobKey);
        jobPublishMsg.setState(JobState.stop);
        publish(jobPublishMsg);
    }

    /**
     * 监听停止定时任务
     *
     * @param jobKey    任务key
     */
    public static void onStopTask(String jobKey) {
        JobTaskFuture jobTaskFuture = TASK_MAP.get(jobKey);
        if (jobTaskFuture != null) {
            jobTaskFuture.stop();
        }
    }

    /**
     * 开启定时任务
     *
     * @param jobKey    任务key
     */
    public static void startTask(String jobKey) {
        JobPublishMsg jobPublishMsg = new JobPublishMsg();
        jobPublishMsg.setJobKey(jobKey);
        jobPublishMsg.setState(JobState.active);
        publish(jobPublishMsg);
    }

    /**
     * 监听启用定时任务
     *
     * @param jobKey    任务key
     */
    public static void onStartTask(String jobKey) {
        JobTaskFuture jobTaskFuture = TASK_MAP.get(jobKey);
        if (jobTaskFuture != null) {
            jobTaskFuture.start();
        }
    }

    /**
     * 立即执行
     *
     * @param jobKey    任务key
     */
    public static void quickExecute(String jobKey) {
        JobPublishMsg jobPublishMsg = new JobPublishMsg();
        jobPublishMsg.setJobKey(jobKey);
        publish(jobPublishMsg);
    }

    /**
     * 监听立即执行任务
     *
     * @param jobKey    任务key
     */
    public static void onQuickExecute(String jobKey) {
        JobTaskFuture jobTaskFuture = TASK_MAP.get(jobKey);
        if (jobTaskFuture != null) {
            EXECUTOR_SERVICE.execute(jobTaskFuture::doExecute);
        }
    }

    /**
     * TODO 发布
     *
     * @param jobPublishMsg  发布消息
     */
    private static void publish(JobPublishMsg jobPublishMsg) {
    }
}
