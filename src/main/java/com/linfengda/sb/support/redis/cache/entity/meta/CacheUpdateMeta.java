package com.linfengda.sb.support.redis.cache.entity.meta;

import lombok.Data;

/**
 * @description:
 * @author: linfengda
 * @date: 2020-07-22 18:54
 */
@Data
public class CacheUpdateMeta extends CacheDeleteMeta {
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
