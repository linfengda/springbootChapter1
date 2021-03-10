package com.lfd.soa.srv.demo.support.redis.cache.annotation;

import com.lfd.soa.srv.demo.support.redis.cache.entity.type.DataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 更新缓存注解：如果缓存中没有，不去更新，缓存中有才更新
 *
 * @author linfengda
 * @create 2020-09-27 15:08
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UpdateCache {
    /**
     * 缓存类型
     */
    DataType type() default DataType.OBJECT;
    /**
     * 缓存前缀，建议使用方法名
     * @return
     */
    String prefix() default "";
    /**
     * 缓存失效时间
     */
    long timeOut() default -1L;
    /**
     * 缓存失效时间单位
     */
    TimeUnit timeUnit() default TimeUnit.HOURS;
    /**
     * 是否防止缓存雪崩：通过叠加随机时间防止缓存雪崩
     * @return
     */
    boolean preCacheSnowSlide() default false;
    /**
     * 防止缓存雪崩随机时间范围
     * @return
     */
    long preCacheSnowSlideTime() default 60*60*1000;
}
