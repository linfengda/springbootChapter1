package com.linfengda.sb.support.cache.builder.strategy;

import com.linfengda.sb.support.cache.entity.meta.CacheMethodMeta;

/**
 * 描述: 不同数据类型缓存实现不同策略
 *
 * @author: linfengda
 * @date: 2020-07-08 16:15
 */
public interface CacheStrategy {
    /**
     * 查询缓存
     * @param cacheMethodMeta   缓存方法信息
     * @return
     */
    Object getCache(CacheMethodMeta cacheMethodMeta);

    /**
     * 缓存
     * @param cacheMethodMeta   缓存方法信息
     * @param value
     */
    void setCache(CacheMethodMeta cacheMethodMeta, Object value);
}
