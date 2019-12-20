package com.linfengda.sb.chapter1.common.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 描述: Cache注解
 *
 * @author linfengda
 * @create 2019-07-12 17:14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {

    String prefix() default "";

    String[] keys() default {""};

    long timeOut() default 60L;

    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
