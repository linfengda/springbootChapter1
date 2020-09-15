package com.linfengda.sb.support.redis.cache.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述: 缓存额外可选，稳定性策略
 *
 * @author: linfengda
 * @date: 2020-07-10 17:48
 */
@Getter
@AllArgsConstructor
public enum CacheExtraStrategy {
    /**
     * 防止缓存雪崩：通过叠加随机时间防止缓存雪崩
     */
    PRV_CACHE_SNOW_SLIDE,
    /**
     * 防止缓存击穿，使用分布式锁限制单个线程加载缓存，来防止热点key失效后大量线程访问DB
     */
    PRV_CACHE_HOT_KEY_MULTI_LOAD,
    ;
}
