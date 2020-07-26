package com.linfengda.sb.support.redis.cache.handler.strategy.impl;

import com.linfengda.sb.support.redis.cache.builder.HashKey;
import com.linfengda.sb.support.redis.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.redis.cache.handler.strategy.AbstractCacheStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 描述: hash
 *
 * @author: linfengda
 * @date: 2020-07-08 16:22
 */
@Slf4j
public class HashCacheStrategy extends AbstractCacheStrategy {

    @Override
    public Object doGetCache(CacheParamDTO param) {
        HashKey hashKey = param.getHashKey();
        Object value = getSimpleRedisTemplate().hashGet(hashKey.getKey(), hashKey.getHashKey());
        return value;
    }

    @Override
    public void doSetCache(CacheParamDTO param, Object value) {
        HashKey hashKey = param.getHashKey();
        getSimpleRedisTemplate().hashPut(hashKey.getKey(), hashKey.getHashKey(), value, param.getTimeOutMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public Boolean hasKey(CacheParamDTO param) {
        HashKey hashKey = param.getHashKey();
        return getSimpleRedisTemplate().hasHashKey(hashKey.getKey(), hashKey.getHashKey());
    }

    @Override
    public Long getCurrentCacheSize(CacheParamDTO param) {
        Set<String> set = getSimpleRedisTemplate().hashKeys(param.getHashKey().getKey());
        if (CollectionUtils.isEmpty(set)) {
            return 0L;
        }
        return (long) set.size();
    }
}
