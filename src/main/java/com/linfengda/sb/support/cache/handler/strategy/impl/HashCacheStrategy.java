package com.linfengda.sb.support.cache.handler.strategy.impl;

import com.linfengda.sb.support.cache.builder.CacheParamBuilder;
import com.linfengda.sb.support.cache.builder.HashKey;
import com.linfengda.sb.support.cache.config.Constant;
import com.linfengda.sb.support.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.cache.entity.type.CacheStableStrategy;
import com.linfengda.sb.support.cache.handler.strategy.CacheStrategy;
import com.linfengda.sb.support.cache.redis.RedisSupportHolder;
import com.linfengda.sb.support.cache.redis.SimpleRedisTemplate;
import com.linfengda.sb.support.cache.util.CacheUtil;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 描述: hash
 *
 * @author: linfengda
 * @date: 2020-07-08 16:22
 */
public class HashCacheStrategy implements CacheStrategy {

    @Override
    public Object getCache(CacheParamDTO param) {
        SimpleRedisTemplate simpleRedisTemplate = RedisSupportHolder.getSimpleRedisTemplate();
        HashKey hashKey = CacheParamBuilder.INSTANCE.buildHashKey(param);
        Object value = simpleRedisTemplate.hashGet(hashKey.getKey(), hashKey.getHashKey());
        return value;
    }

    @Override
    public void setCache(CacheParamDTO param, Object value) {
        SimpleRedisTemplate simpleRedisTemplate = RedisSupportHolder.getSimpleRedisTemplate();
        HashKey hashKey = CacheParamBuilder.INSTANCE.buildHashKey(param);
        long timeOutMillis = param.getTimeUnit().toMillis(param.getTimeOut());
        if (Constant.DEFAULT_NO_EXPIRE_TIME == timeOutMillis) {
            simpleRedisTemplate.hashPut(hashKey.getKey(), hashKey.getHashKey(), value);
        }else {
            if (param.getStrategies().contains(CacheStableStrategy.NO_CACHE_SNOW_SLIDE)) {
                timeOutMillis = CacheUtil.getRandomTime(timeOutMillis);
            }
            simpleRedisTemplate.hashPut(hashKey.getKey(), hashKey.getHashKey(), value, timeOutMillis, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public Boolean hasKey(CacheParamDTO param) {
        SimpleRedisTemplate simpleRedisTemplate = RedisSupportHolder.getSimpleRedisTemplate();
        HashKey hashKey = CacheParamBuilder.INSTANCE.buildHashKey(param);
        return simpleRedisTemplate.hasHashKey(hashKey.getKey(), hashKey.getHashKey());
    }

    @Override
    public Long getCurrentCacheSize(CacheParamDTO param) {
        SimpleRedisTemplate simpleRedisTemplate = RedisSupportHolder.getSimpleRedisTemplate();
        Set<String> set = simpleRedisTemplate.hashKeys(param.getPrefix());
        if (CollectionUtils.isEmpty(set)) {
            return 0L;
        }
        return (long) set.size();
    }
}
