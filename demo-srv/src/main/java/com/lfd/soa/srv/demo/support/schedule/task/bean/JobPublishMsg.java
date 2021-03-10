package com.lfd.soa.srv.demo.support.schedule.task.bean;

import lombok.Data;

/**
 * @author linfengda
 * @date 2021-02-03 17:10
 */
@Data
public class JobPublishMsg {
    /**
     * 任务id
     */
    private String jobKey;
    /**
     * 表达式
     */
    private String cron;
    /**
     * 任务状态
     */
    private JobState state;
}
