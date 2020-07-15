package com.linfengda.sb.support.cache.handler.strategy.impl;

import com.linfengda.sb.support.cache.builder.HashKey;
import com.linfengda.sb.support.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.cache.entity.type.CacheExtraStrategy;
import com.linfengda.sb.support.cache.handler.strategy.AbstractCacheStrategy;
import com.linfengda.sb.support.cache.util.CacheUtil;
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
    public Object getCache(CacheParamDTO param) {
        HashKey hashKey = param.getHashKey();
        Object value = getSimpleRedisTemplate().hashGet(hashKey.getKey(), hashKey.getHashKey());
        return value;
    }

    @Override
    public void setCache(CacheParamDTO param, Object value) {
        HashKey hashKey = param.getHashKey();
        Long timeOutMillis = param.getTimeUnit().toMillis(param.getTimeOut());
        if (param.getStrategies().contains(CacheExtraStrategy.NO_CACHE_SNOW_SLIDE)) {
            timeOutMillis = CacheUtil.getRandomTime(timeOutMillis);
        }
        getSimpleRedisTemplate().hashPut(hashKey.getKey(), hashKey.getHashKey(), value, timeOutMillis, TimeUnit.MILLISECONDS);
    }

    @Override
    public Boolean hasKey(CacheParamDTO param) {
        HashKey hashKey = param.getHashKey();
        return getSimpleRedisTemplate().hasHashKey(hashKey.getKey(), hashKey.getHashKey());
    }

    @Override
    public Long getCurrentCacheSize(CacheParamDTO param) {
        Set<String> set = getSimpleRedisTemplate().hashKeys(param.getPrefix());
        if (CollectionUtils.isEmpty(set)) {
            return 0L;
        }
        return (long) set.size();
    }
}
