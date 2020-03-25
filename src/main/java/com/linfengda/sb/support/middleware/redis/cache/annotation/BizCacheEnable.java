package com.linfengda.sb.support.middleware.redis.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 缓存注解
 *
 * @author linfengda
 * @create 2019-07-12 17:14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BizCacheEnable {
    /**
     * 缓存前缀，建议使用方法名
     * @return
     */
    String prefix() default "";
    /**
     * 缓存key列表，对应方法参数名称
     * @return
     */
    String[] keys() default {""};
    /**
     * 缓存失效时间
     * @return
     */
    long timeOut() default 60L;
    /**
     * 缓存失效时间单位
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
