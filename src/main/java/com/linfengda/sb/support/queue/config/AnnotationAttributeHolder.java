package com.linfengda.sb.support.queue.config;

import lombok.Getter;
import org.springframework.core.annotation.AnnotationAttributes;

/**
 * @description 注解缓存元数据
 * @author linfengda
 * @date 2020-09-15 11:59
 */
@Getter
public enum AnnotationAttributeHolder {
    /**
     * 单例
     */
    INSTANCE;
    private AnnotationAttributes attributes = null;

    public void init(AnnotationAttributes att) {
        attributes = att;
    }
}
