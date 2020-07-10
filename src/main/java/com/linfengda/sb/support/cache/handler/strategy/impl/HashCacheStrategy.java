package com.linfengda.sb.support.cache.handler.strategy.impl;

import com.linfengda.sb.support.cache.builder.CacheParamBuilder;
import com.linfengda.sb.support.cache.builder.HashKey;
import com.linfengda.sb.support.cache.handler.strategy.CacheStrategy;
import com.linfengda.sb.support.cache.config.Constant;
import com.linfengda.sb.support.cache.entity.dto.CacheParamDTO;
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
    public Object getCache(CacheParamDTO param) {
        JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
        HashKey hashKey = CacheParamBuilder.INSTANCE.buildHashKey(param);
        Object value = jacksonRedisTemplate.hashGet(hashKey.getKey(), hashKey.getHashKey());
        return value;
    }

    @Override
    public void setCache(CacheParamDTO param, Object value) {
        JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
        HashKey hashKey = CacheParamBuilder.INSTANCE.buildHashKey(param);
        if (Constant.NO_EXPIRE_TIME.equals(param.getTimeOut())) {
            jacksonRedisTemplate.hashPut(hashKey.getKey(), hashKey.getHashKey(), value);
        }else {
            jacksonRedisTemplate.hashPut(hashKey.getKey(), hashKey.getHashKey(), value, param.getTimeOut(), param.getTimeUnit());
        }
    }
}
