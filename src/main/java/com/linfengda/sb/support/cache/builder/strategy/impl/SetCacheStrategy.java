package com.linfengda.sb.support.cache.builder.strategy.impl;

import com.linfengda.sb.support.cache.builder.CacheKeyBuilder;
import com.linfengda.sb.support.cache.builder.strategy.CacheStrategy;
import com.linfengda.sb.support.cache.entity.meta.CacheMethodMeta;
import com.linfengda.sb.support.cache.manager.RedisTemplateHolder;
import com.linfengda.sb.support.middleware.redis.template.JacksonRedisTemplate;

import java.util.List;
import java.util.Set;

/**
 * 描述: set
 *
 * @author: linfengda
 * @date: 2020-07-08 16:23
 */
public class SetCacheStrategy implements CacheStrategy {

    @Override
    public Object getCache(CacheMethodMeta cacheMethodMeta) {
        JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
        String key = CacheKeyBuilder.INSTANCE.buildObjectKey(cacheMethodMeta);
        Object value = jacksonRedisTemplate.setGet(key, cacheMethodMeta.getReturnType());
        return value;
    }

    @Override
    public void setCache(CacheMethodMeta cacheMethodMeta, Object value) {
        JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
        String key = CacheKeyBuilder.INSTANCE.buildObjectKey(cacheMethodMeta);
        if (value instanceof List) {
            List list = (List) value;
            jacksonRedisTemplate.setAddAll(key, list);
        }else if (value instanceof Set) {
            Set set = (Set) value;
            jacksonRedisTemplate.setAddAll(key, set);
        }else {
            jacksonRedisTemplate.setAdd(key, value);
        }
    }
}
