package com.linfengda.sb.support.cache.handler.strategy.impl;

import com.linfengda.sb.support.cache.builder.CacheParamBuilder;
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
 * 描述: object
 *
 * @author: linfengda
 * @date: 2020-07-08 16:17
 */
public class ObjCacheStrategy implements CacheStrategy {

    @Override
    public Object getCache(CacheParamDTO param) {
        SimpleRedisTemplate simpleRedisTemplate = RedisSupportHolder.getSimpleRedisTemplate();
        String key = CacheParamBuilder.INSTANCE.buildObjectKey(param);
        Object value = simpleRedisTemplate.getObject(key);
        return value;
    }

    @Override
    public void setCache(CacheParamDTO param, Object value) {
        if (isMaxCacheSize(param)) {
            if (param.getStrategies().contains(CacheStableStrategy.MAX_SIZE_STRATEGY_ABANDON)) {

            }
        }
        SimpleRedisTemplate simpleRedisTemplate = RedisSupportHolder.getSimpleRedisTemplate();
        String key = CacheParamBuilder.INSTANCE.buildObjectKey(param);
        long timeOutMillis = param.getTimeUnit().toMillis(param.getTimeOut());
        if (Constant.DEFAULT_NO_EXPIRE_TIME == timeOutMillis) {
            simpleRedisTemplate.setObject(key, value);
        }else {
            if (param.getStrategies().contains(CacheStableStrategy.NO_CACHE_SNOW_SLIDE)) {
                timeOutMillis = CacheUtil.getRandomTime(timeOutMillis);
            }
            simpleRedisTemplate.setObject(key, value, timeOutMillis, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public Boolean hasKey(CacheParamDTO param) {
        SimpleRedisTemplate simpleRedisTemplate = RedisSupportHolder.getSimpleRedisTemplate();
        String key = CacheParamBuilder.INSTANCE.buildObjectKey(param);
        return simpleRedisTemplate.hasKey(key);
    }

    @Override
    public Long getCurrentCacheSize(CacheParamDTO param) {
        String prefix = param.getPrefix();
        String keyPattern = prefix + Constant.ASTERISK;
        SimpleRedisTemplate simpleRedisTemplate = RedisSupportHolder.getSimpleRedisTemplate();
        Set<String> set = simpleRedisTemplate.keys(keyPattern);
        if (CollectionUtils.isEmpty(set)) {
            return 0L;
        }
        return (long) set.size();
    }
}
