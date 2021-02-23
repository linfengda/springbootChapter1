package com.linfengda.sb.support.schedule.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * 定时任务注解
 *
 * @author linfengda
 * @date 2021-02-03 15:50
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface JobMapping {

    /**
     * 任务
     * @return
     */
    String name() default "";

    /**
     * 任务描述
     *
     * @return
     */
    String desc() default "";

    /**
     * 表达式
     *
     * @return
     */
    String cron() default "";

    /**
     * 业务类型
     *
     * @return
     */
    String moduleType() default "";

    /**
     * 是否并行
     *
     * @return
     */
    boolean distribute() default true;
}
