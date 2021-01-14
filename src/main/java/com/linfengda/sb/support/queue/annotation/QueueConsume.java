package com.linfengda.sb.support.queue.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 队列消费注解
 * @author: linfengda
 * @date: 2021-01-14 22:52
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueueConsume {
    /**
     * 队列名称
     * @return
     */
    String name() default "";
    /**
     * queue
     * @return
     */
    String queue() default "";
    /**
     * virtualHost
     * @return
     */
    String virtualHost() default "";
    /**
     * exchange
     * @return
     */
    String exchange() default "";
    /**
     * routingKey
     * @return
     */
    String routingKey() default "";
    /**
     * 处理器
     * @return
     */
    String processor() default "";
}
