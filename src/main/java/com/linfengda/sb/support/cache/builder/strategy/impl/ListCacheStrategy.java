package com.linfengda.sb.support.cache.builder.strategy.impl;

import com.linfengda.sb.support.cache.builder.CacheKeyBuilder;
import com.linfengda.sb.support.cache.builder.strategy.CacheStrategy;
import com.linfengda.sb.support.cache.entity.meta.CacheMethodMeta;
import com.linfengda.sb.support.cache.manager.RedisTemplateHolder;
import com.linfengda.sb.support.middleware.redis.template.JacksonRedisTemplate;

import java.util.List;
import java.util.Set;

/**
 * 描述: list
 *
 * @author: linfengda
 * @date: 2020-07-08 16:22
 */
public class ListCacheStrategy implements CacheStrategy {

    @Override
    public Object getCache(CacheMethodMeta cacheMethodMeta) {
        JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
        String key = CacheKeyBuilder.INSTANCE.buildObjectKey(cacheMethodMeta);
        Object value = jacksonRedisTemplate.listGet(key, cacheMethodMeta.getReturnType());
        return value;
    }

    @Override
    public void setCache(CacheMethodMeta cacheMethodMeta, Object value) {
        JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
        String key = CacheKeyBuilder.INSTANCE.buildObjectKey(cacheMethodMeta);
        if (value instanceof List) {
            List list = (List) value;
            jacksonRedisTemplate.listAddAll(key, list);
        }else if (value instanceof Set) {
            Set set = (Set) value;
            jacksonRedisTemplate.listAddAll(key, set);
        }else {
            jacksonRedisTemplate.listAdd(key, value);
        }
    }
}
