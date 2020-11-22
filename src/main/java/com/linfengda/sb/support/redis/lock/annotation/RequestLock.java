package com.linfengda.sb.support.redis.lock.annotation;

/**
 * @description: 请求业务锁
 * @author: linfengda
 * @date: 2020-11-16 11:52
 */
public @interface RequestLock {

    String desc() default "";
}
