package com.lfd.srv.demo.support.schedule.annotation;

import java.lang.annotation.*;

/**
 * 定时任务控制类注解
 *
 * @author linfengda
 * @date 2021-02-03 15:46
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JobController {
}
