package com.linfengda.sb.support.cache.handler.strategy.impl;

import com.linfengda.sb.support.cache.config.RedisSupportHolder;
import com.linfengda.sb.support.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.cache.entity.type.CacheExtraStrategy;
import com.linfengda.sb.support.cache.handler.strategy.AbstractCacheStrategy;
import com.linfengda.sb.support.cache.redis.template.SimpleRedisTemplate;
import com.linfengda.sb.support.cache.util.CacheUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 描述: list
 *
 * @author: linfengda
 * @date: 2020-07-08 16:22
 */
@Slf4j
public class ListCacheStrategy extends AbstractCacheStrategy {

    @Override
    public Object getCache(CacheParamDTO param) {
        Object value = getSimpleRedisTemplate().listGetAll(param.getKey());
        return value;
    }

    @Override
    public void setCache(CacheParamDTO param, Object value) {
        Long timeOutMillis = param.getTimeUnit().toMillis(param.getTimeOut());
        if (param.getStrategies().contains(CacheExtraStrategy.NO_CACHE_SNOW_SLIDE)) {
            timeOutMillis = CacheUtil.getRandomTime(timeOutMillis);
        }
        if (value instanceof List) {
            List list = (List) value;
            getSimpleRedisTemplate().listAddAll(param.getKey(), list, timeOutMillis, TimeUnit.MILLISECONDS);
        }else if (value instanceof Set) {
            Set set = (Set) value;
            getSimpleRedisTemplate().listAddAll(param.getKey(), set, timeOutMillis, TimeUnit.MILLISECONDS);
        }else {
            getSimpleRedisTemplate().listAdd(param.getKey(), value, timeOutMillis, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public Boolean hasKey(CacheParamDTO param) {
        return null;
    }

    @Override
    public Long getCurrentCacheSize(CacheParamDTO param) {
        Long size = getSimpleRedisTemplate().opsForList().size(param.getPrefix());
        return size;
    }
}
