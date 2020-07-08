package com.linfengda.sb.support.cache.builder.strategy.impl;

import com.linfengda.sb.support.cache.builder.CacheKeyBuilder;
import com.linfengda.sb.support.cache.builder.strategy.CacheStrategy;
import com.linfengda.sb.support.cache.entity.meta.CacheMethodMeta;
import com.linfengda.sb.support.cache.manager.RedisTemplateHolder;
import com.linfengda.sb.support.middleware.redis.template.JacksonRedisTemplate;

/**
 * 描述: object
 *
 * @author: linfengda
 * @date: 2020-07-08 16:17
 */
public class ObjCacheStrategy implements CacheStrategy {

    @Override
    public Object getCache(CacheMethodMeta cacheMethodMeta) {
        JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
        String key = CacheKeyBuilder.INSTANCE.buildObjectKey(cacheMethodMeta);
        Object value = jacksonRedisTemplate.getObject(key, cacheMethodMeta.getReturnType());
        return value;
    }

    @Override
    public void setCache(CacheMethodMeta cacheMethodMeta, Object value) {
        JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
        String key = CacheKeyBuilder.INSTANCE.buildObjectKey(cacheMethodMeta);
        jacksonRedisTemplate.setObject(key, value);
    }
}
