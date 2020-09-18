package com.linfengda.sb.support.redis.cache.annotation;

import com.linfengda.sb.support.redis.cache.entity.type.CacheMaxSizeStrategy;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 查询缓存注解
 *
 * @author linfengda
 * @create 2020-03-26 10:34
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryCache {
    /**
     * 缓存类型
     */
    DataType type() default DataType.OBJECT;
    /**
     * 缓存前缀，建议使用方法名
     */
    String prefix() default "";
    /**
     * 缓存失效时间
     */
    long timeOut() default 0;
    /**
     * 缓存失效时间单位
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
    /**
     * 最大缓存数量
     */
    long maxSize() default 0;
    /**
     * 缓存最大数量淘汰策略
     */
    CacheMaxSizeStrategy maxSizeStrategy() default CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_ABANDON;
    /**
     * 当lru缓存大小超出限制时，删除的key数量
     * @return
     */
    int deleteLruBatchNum() default 1;
    /**
     * 等待缓存加载自旋时间（原则上不能超过查询数据库耗费的时间，不然取得的是反效果）
     * @return
     */
    long spinTime() default 50;
    /**
     * 等待缓存加载自旋次数
     * @return
     */
    int maxSpinCount() default 3;
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
    /**
     * 防止缓存击穿，使用分布式锁限制单个线程加载缓存，来防止热点key失效后大量线程访问DB
     * @return
     */
    boolean preCacheHotKeyMultiLoad() default false;
}
