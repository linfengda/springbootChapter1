package com.lfd.srv.demo.support.queue.annotation;

import com.lfd.srv.demo.support.queue.config.QueueServiceConfigSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @description 开启rabbitmq队列消费扩展
 * @author linfengda
 * @date 2021-01-15 11:43
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({QueueServiceConfigSelector.class})
public @interface EnableQueueService {
    /**
     * listener所在包路径
     * @return
     */
    String baseScanPackage() default "";
}
