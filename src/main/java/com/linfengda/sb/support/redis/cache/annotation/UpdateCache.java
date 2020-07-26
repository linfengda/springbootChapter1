package com.linfengda.sb.support.redis.cache.annotation;

import com.linfengda.sb.support.redis.cache.entity.type.CacheExtraStrategy;
import com.linfengda.sb.support.redis.cache.entity.type.CacheMaxSizeStrategy;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 更新缓存注解
 *
 * @author linfengda
 * @create 2020-03-24 15:08
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
     * 缓存策略
     */
    CacheExtraStrategy[] strategies() default {};
    /**
     * 缓存最大数量淘汰策略
     */
    CacheMaxSizeStrategy maxSizeStrategy() default CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_ABANDON;
    /**
     * 最大缓存数量
     */
    long maxSize() default -1L;
}
