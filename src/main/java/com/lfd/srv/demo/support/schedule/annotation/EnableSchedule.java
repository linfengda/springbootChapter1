package com.lfd.srv.demo.support.schedule.annotation;

import com.lfd.srv.demo.support.schedule.config.ScheduleConfigSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启schedule
 *
 * @author linfengda
 * @date 2021-02-03 16:13
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ScheduleConfigSelector.class})
public @interface EnableSchedule {

    /**
     * 扫描包pattern
     * @return
     */
    String basePackage() default "";
}
