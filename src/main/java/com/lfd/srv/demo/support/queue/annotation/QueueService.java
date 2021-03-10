package com.lfd.srv.demo.support.queue.annotation;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 监听服务注解
 * @author linfengda
 * @date 2020-11-16 11:52
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueueService {
    /**
     * 监听服务名称
     * @return
     */
    String name() default "";
    /**
     * {@link SimpleRabbitListenerContainerFactory}
     * @return
     */
    String containerFactory() default "";
}
