package com.linfengda.sb.support.cache.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述: 缓存针对策略
 *
 * @author: linfengda
 * @date: 2020-07-10 17:48
 */
@Getter
@AllArgsConstructor
public enum CachePolicy {
    /**
     * 防止缓存雪崩：对于同一时间加载的缓存，可以叠加随机时间防止缓存雪崩
     */
    PREVENT_CACHE_SNOW_SLIDE("", "防止缓存雪崩"),
    /**
     * 限制单个线程加载缓存：可以防止缓存击穿后过多线程同时更新缓存
     */
    KEEP_ONE_THREAD_LOAD_CACHE("", "限制单个线程加载缓存"),
    /**
     * 缓存空值：防止缓存穿透
     */
    CACHE_NULL_VALUE("", "缓存空值"),
    /**
     * 启动布隆过滤器：防止缓存穿透
     */
    USE_BLOOM_FILTER("", "启动布隆过滤器"),
    /**
     * 限制缓存规模
     */
    LIMIT_CACHE_SIZE("", "限制缓存规模"),
    ;

    /**
     * 策略
     */
    private String code;
    /**
     * 策略描述
     */
    private String desc;
}
