package com.linfengda.sb.support.redis.lock.annotation;

/**
 * @description: 请求业务锁key
 * @author: linfengda
 * @date: 2020-11-16 11:55
 */
public @interface RequestLockKey {

    /**
     * 当多个参数作为key时，需要指定key的顺序
     * @return
     */
    int index() default 0;
}
