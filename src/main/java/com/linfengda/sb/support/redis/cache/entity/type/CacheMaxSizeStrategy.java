package com.linfengda.sb.support.redis.cache.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述: 缓存最大数量淘汰策略
 *
 * @author: linfengda
 * @date: 2020-07-20 11:25
 */
@Getter
@AllArgsConstructor
public enum CacheMaxSizeStrategy {
    /**
     * 缓存最大数量淘汰策略：LRU
     */
    MAX_SIZE_STRATEGY_LRU("", "最大缓存策略：LRU"),
    /**
     * 缓存最大数量淘汰策略：抛弃
     */
    MAX_SIZE_STRATEGY_ABANDON("", "最大缓存策略：抛弃"),
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
