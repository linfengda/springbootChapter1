package com.linfengda.sb.support.cache.builder.strategy.impl;

import com.linfengda.sb.support.cache.builder.CacheKeyBuilder;
import com.linfengda.sb.support.cache.builder.strategy.CacheStrategy;
import com.linfengda.sb.support.cache.config.Constant;
import com.linfengda.sb.support.cache.entity.meta.CacheMethodMeta;
import com.linfengda.sb.support.cache.manager.RedisTemplateHolder;
import com.linfengda.sb.support.middleware.redis.template.JacksonRedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
        Object value = jacksonRedisTemplate.setGet(key);
        return value;
    }

    @Override
    public void setCache(CacheMethodMeta cacheMethodMeta, Object value) {
        String key = CacheKeyBuilder.INSTANCE.buildObjectKey(cacheMethodMeta);
        if (value instanceof List) {
            List list = (List) value;
            setValues(key, list, cacheMethodMeta.getTimeOut(), cacheMethodMeta.getTimeUnit());
        }else if (value instanceof Set) {
            Set set = (Set) value;
            setValues(key, set, cacheMethodMeta.getTimeOut(), cacheMethodMeta.getTimeUnit());
        }else {
            setValue(key, value, cacheMethodMeta.getTimeOut(), cacheMethodMeta.getTimeUnit());
        }
    }

    private void setValues(String key, Collection values, Long timeOut, TimeUnit timeUnit) {
        JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
        if (Constant.NO_EXPIRE_TIME.equals(timeOut)) {
            jacksonRedisTemplate.setAddAll(key, values);
        }else {
            jacksonRedisTemplate.setAddAll(key, values, timeOut, timeUnit);
        }
    }

    private void setValue(String key, Object value, Long timeOut, TimeUnit timeUnit) {
        JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
        if (Constant.NO_EXPIRE_TIME.equals(timeOut)) {
            jacksonRedisTemplate.setAdd(key, value);
        }else {
            jacksonRedisTemplate.setAdd(key, value, timeOut, timeUnit);
        }
    }
}
