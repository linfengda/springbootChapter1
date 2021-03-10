package com.lfd.srv.demo.support.schedule.task.bean;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Date;

/**
 * 任务执行trigger
 *
 * @author linfengda
 * @date 2021-02-03 16:53
 */
public class JobTrigger implements Trigger {

    private String cron;

    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {
        CronTrigger trigger = new CronTrigger(cron);
        return trigger.nextExecutionTime(triggerContext);
    }

    public JobTrigger(String cron){
        this.cron = cron;
    }
}
