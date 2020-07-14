package com.linfengda.sb.support.cache.handler.strategy.impl;

import com.linfengda.sb.support.cache.builder.CacheParamBuilder;
import com.linfengda.sb.support.cache.config.Constant;
import com.linfengda.sb.support.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.cache.handler.strategy.CacheStrategy;
import com.linfengda.sb.support.cache.redis.RedisSupportHolder;
import com.linfengda.sb.support.cache.redis.SimpleRedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 描述: list
 *
 * @author: linfengda
 * @date: 2020-07-08 16:22
 */
public class ListCacheStrategy implements CacheStrategy {

    @Override
    public Object getCache(CacheParamDTO param) {
        SimpleRedisTemplate simpleRedisTemplate = RedisSupportHolder.getSimpleRedisTemplate();
        String key = CacheParamBuilder.INSTANCE.buildObjectKey(param);
        Object value = simpleRedisTemplate.listGet(key);
        return value;
    }

    @Override
    public void setCache(CacheParamDTO param, Object value) {
        String key = CacheParamBuilder.INSTANCE.buildObjectKey(param);
        if (value instanceof List) {
            List list = (List) value;
            setValues(key, list, param.getTimeOut(), param.getTimeUnit());
        }else if (value instanceof Set) {
            Set set = (Set) value;
            setValues(key, set, param.getTimeOut(), param.getTimeUnit());
        }else {
            setValue(key, value, param.getTimeOut(), param.getTimeUnit());
        }
    }

    @Override
    public Boolean hasKey(CacheParamDTO param) {
        return null;
    }

    private void setValues(String key, Collection values, Long timeOut, TimeUnit timeUnit) {
        SimpleRedisTemplate simpleRedisTemplate = RedisSupportHolder.getSimpleRedisTemplate();
        if (Constant.DEFAULT_NO_EXPIRE_TIME.equals(timeOut)) {
            simpleRedisTemplate.listAddAll(key, values);
        }else {
            simpleRedisTemplate.listAddAll(key, values, timeOut, timeUnit);
        }
    }

    private void setValue(String key, Object value, Long timeOut, TimeUnit timeUnit) {
        SimpleRedisTemplate simpleRedisTemplate = RedisSupportHolder.getSimpleRedisTemplate();
        if (Constant.DEFAULT_NO_EXPIRE_TIME.equals(timeOut)) {
            simpleRedisTemplate.listAdd(key, value);
        }else {
            simpleRedisTemplate.listAdd(key, value, timeOut, timeUnit);
        }
    }

    @Override
    public Long getCurrentCacheSize(CacheParamDTO param) {
        SimpleRedisTemplate simpleRedisTemplate = RedisSupportHolder.getSimpleRedisTemplate();
        Long size = simpleRedisTemplate.opsForList().size(param.getPrefix());
        return size;
    }
}
