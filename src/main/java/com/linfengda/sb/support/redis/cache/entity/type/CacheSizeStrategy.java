package com.linfengda.sb.support.redis.cache.entity.type;

/**
 * 描述: 缓存最大数量类型
 *
 * @author: linfengda
 * @date: 2020-07-15 16:08
 */
public enum CacheSizeStrategy {
    /**
     * 未限制缓存数量
     */
    UN_LIMIT,
    /**
     * 正常缓存数量
     */
    NORMAL_SIZE,
    /**
     * 超出最大缓存数量
     */
    OVER_SIZE,
    ;
}
