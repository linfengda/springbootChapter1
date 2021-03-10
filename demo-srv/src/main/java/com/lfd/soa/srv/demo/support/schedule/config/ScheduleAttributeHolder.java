package com.lfd.soa.srv.demo.support.schedule.config;

import lombok.Getter;
import org.springframework.core.annotation.AnnotationAttributes;

/**
 * @author linfengda
 * @date 2021-02-04 11:23
 */
@Getter
public enum ScheduleAttributeHolder {
    /**
     * 单例
     */
    INSTANCE;
    private AnnotationAttributes attributes = null;

    public void init(AnnotationAttributes att) {
        attributes = att;
    }
}
