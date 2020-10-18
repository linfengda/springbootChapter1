package com.linfengda.sb.support.redis.cache.entity.type;

/**
 * 描述: 缓存最大数量淘汰策略
 *
 * @author: linfengda
 * @date: 2020-07-26 11:25
 */
public enum CacheMaxSizeStrategy {
    /**
     * 缓存最大数量淘汰策略：抛弃
     */
    MAX_SIZE_STRATEGY_ABANDON,
    /**
     * 缓存最大数量淘汰策略：LRU
     */
    MAX_SIZE_STRATEGY_LRU,
    ;

    public static boolean hasStrategy(CacheMaxSizeStrategy strategy) {
        return MAX_SIZE_STRATEGY_ABANDON == strategy || MAX_SIZE_STRATEGY_LRU == strategy;
    }
}
