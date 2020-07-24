package com.linfengda.sb.support.cache.handler.strategy.impl;

import com.linfengda.sb.support.cache.config.Constant;
import com.linfengda.sb.support.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.cache.entity.type.CacheMaxSizeStrategy;
import com.linfengda.sb.support.cache.entity.type.CacheSizeStrategy;
import com.linfengda.sb.support.cache.handler.strategy.AbstractCacheStrategy;
import com.linfengda.sb.support.cache.util.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 描述: object
 *
 * @author: linfengda
 * @date: 2020-07-08 16:17
 */
@Slf4j
public class ObjCacheStrategy extends AbstractCacheStrategy {

    @Override
    public Object getCache(CacheParamDTO param) {
        Object value = getSimpleRedisTemplate().getObject(param.getKey());
        if (null == value) {
            return null;
        }
        if (CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_LRU == param.getMaxSizeStrategy()) {
            getSimpleRedisTemplate().opsForZSet().add(param.getLruKey(), param.getKey(), CacheUtil.getKeyLruScore());
        }
        return value;
    }

    @Override
    public void setCache(CacheParamDTO param, Object value) {
        if (null == value) {
            return;
        }
        CacheSizeStrategy cacheSizeStrategy = checkCacheSize(param);
        if (CacheSizeStrategy.OVER_SIZE == cacheSizeStrategy) {
            log.debug("当前缓存大小超过限制：{}，将不再缓存数据！", param.getMaxSize());
            return;
        }
        getSimpleRedisTemplate().setObject(param.getKey(), value, param.getTimeOutMillis(), TimeUnit.MILLISECONDS);
        if (CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_LRU == param.getMaxSizeStrategy()) {
            getSimpleRedisTemplate().opsForZSet().add(param.getLruKey(), param.getKey(), CacheUtil.getKeyLruScore());
        }
    }

    @Override
    public Boolean hasKey(CacheParamDTO param) {
        String key = param.getKey();
        return getSimpleRedisTemplate().hasKey(key);
    }

    @Override
    public Long getCurrentCacheSize(CacheParamDTO param) {
        String keyPattern = param.getPrefix() + Constant.ASTERISK;
        Set<String> set = getSimpleRedisTemplate().keys(keyPattern);
        if (CollectionUtils.isEmpty(set)) {
            return 0L;
        }
        return (long) set.size();
    }
}
