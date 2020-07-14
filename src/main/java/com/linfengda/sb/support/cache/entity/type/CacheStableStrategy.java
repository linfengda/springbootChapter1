package com.linfengda.sb.support.cache.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述: 缓存稳定性策略
 *
 * @author: linfengda
 * @date: 2020-07-10 17:48
 */
@Getter
@AllArgsConstructor
public enum CacheStableStrategy {
    /**
     * 防止缓存雪崩：对于同一时间加载的缓存，通过叠加随机时间防止缓存雪崩
     */
    NO_CACHE_SNOW_SLIDE("", "防止缓存雪崩"),
    /**
     * 防止缓存击穿，使用分布式锁限制单个线程加载缓存，来防止热点key失效后大量线程访问DB
     */
    NO_CACHE_HOT_KEY_MULTI_LOAD("", "防止缓存击穿"),
    /**
     * 最大缓存策略：抛弃
     */
    MAX_SIZE_STRATEGY_ABANDON("", "最大缓存策略：抛弃"),
    /**
     * 最大缓存策略：LRU
     */
    MAX_SIZE_STRATEGY_LRU("", "最大缓存策略：LRU"),
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
