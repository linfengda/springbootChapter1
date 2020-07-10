package com.linfengda.sb.support.cache.handler.strategy.impl;

import com.linfengda.sb.support.cache.builder.CacheParamBuilder;
import com.linfengda.sb.support.cache.handler.strategy.CacheStrategy;
import com.linfengda.sb.support.cache.config.Constant;
import com.linfengda.sb.support.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.cache.manager.RedisTemplateHolder;
import com.linfengda.sb.support.middleware.redis.template.JacksonRedisTemplate;

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
        JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
        String key = CacheParamBuilder.INSTANCE.buildObjectKey(param);
        Object value = jacksonRedisTemplate.listGet(key);
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

    private void setValues(String key, Collection values, Long timeOut, TimeUnit timeUnit) {
        JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
        if (Constant.NO_EXPIRE_TIME.equals(timeOut)) {
            jacksonRedisTemplate.listAddAll(key, values);
        }else {
            jacksonRedisTemplate.listAddAll(key, values, timeOut, timeUnit);
        }
    }

    private void setValue(String key, Object value, Long timeOut, TimeUnit timeUnit) {
        JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
        if (Constant.NO_EXPIRE_TIME.equals(timeOut)) {
            jacksonRedisTemplate.listAdd(key, value);
        }else {
            jacksonRedisTemplate.listAdd(key, value, timeOut, timeUnit);
        }
    }
}
