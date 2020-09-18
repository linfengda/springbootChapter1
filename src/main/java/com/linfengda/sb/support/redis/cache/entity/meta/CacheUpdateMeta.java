package com.linfengda.sb.support.redis.cache.entity.meta;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: linfengda
 * @date: 2020-09-17 18:54
 */
@Data
public class CacheUpdateMeta {
    /**
     * 缓存失效时间
     */
    private Long timeOut;
    /**
     * 缓存失效时间单位
     */
    TimeUnit timeUnit;
    /**
     * 是否防止缓存雪崩
     * @return
     */
    private Boolean preCacheSnowSlide;
    /**
     * 防止缓存雪崩随机时间范围
     * @return
     */
    private Long preCacheSnowSlideTime;
}
