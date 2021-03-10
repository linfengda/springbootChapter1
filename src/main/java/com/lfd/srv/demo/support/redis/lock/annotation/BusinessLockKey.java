package com.lfd.srv.demo.support.redis.lock.annotation;

import java.lang.annotation.*;

/**
 * @description 业务锁key
 * @author linfengda
 * @date 2020-11-16 11:55
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface BusinessLockKey {

    /**
     * 当多个参数作为key时，需要指定key的顺序
     * @return
     */
    int index() default 0;
}
