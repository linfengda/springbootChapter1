package com.linfengda.sb.support.cache.builder.strategy.impl;

import com.linfengda.sb.support.cache.builder.CacheKeyBuilder;
import com.linfengda.sb.support.cache.builder.HashKey;
import com.linfengda.sb.support.cache.builder.strategy.CacheStrategy;
import com.linfengda.sb.support.cache.config.Constant;
import com.linfengda.sb.support.cache.entity.meta.CacheMethodMeta;
import com.linfengda.sb.support.cache.manager.RedisTemplateHolder;
import com.linfengda.sb.support.middleware.redis.template.JacksonRedisTemplate;

/**
 * 描述: hash
 *
 * @author: linfengda
 * @date: 2020-07-08 16:22
 */
public class HashCacheStrategy implements CacheStrategy {

    @Override
    public Object getCache(CacheMethodMeta cacheMethodMeta) {
        JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
        HashKey hashKey = CacheKeyBuilder.INSTANCE.buildHashKey(cacheMethodMeta);
        Object value = jacksonRedisTemplate.hashGet(hashKey.getKey(), hashKey.getHashKey());
        return value;
    }

    @Override
    public void setCache(CacheMethodMeta cacheMethodMeta, Object value) {
        JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
        HashKey hashKey = CacheKeyBuilder.INSTANCE.buildHashKey(cacheMethodMeta);
        if (Constant.NO_EXPIRE_TIME.equals(cacheMethodMeta.getTimeOut())) {
            jacksonRedisTemplate.hashPut(hashKey.getKey(), hashKey.getHashKey(), value);
        }else {
            jacksonRedisTemplate.hashPut(hashKey.getKey(), hashKey.getHashKey(), value, cacheMethodMeta.getTimeOut(), cacheMethodMeta.getTimeUnit());
        }
    }
}
