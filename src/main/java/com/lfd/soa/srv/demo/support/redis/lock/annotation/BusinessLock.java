package com.lfd.soa.srv.demo.support.redis.lock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 业务锁注解
 * @author linfengda
 * @date 2020-11-16 11:52
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BusinessLock {

    String prefix() default "";

    String desc() default "";
}
