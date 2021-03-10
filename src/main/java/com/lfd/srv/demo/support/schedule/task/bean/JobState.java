package com.lfd.srv.demo.support.schedule.task.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author linfengda
 * @date 2021-02-03 17:16
 */
@Getter
@AllArgsConstructor
public enum JobState {
    /**
     * 启动
     */
    active,
    /**
     * 暂停
     */
    stop
}
