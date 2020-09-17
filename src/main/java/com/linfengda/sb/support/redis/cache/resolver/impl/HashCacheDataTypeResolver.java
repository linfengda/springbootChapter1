package com.linfengda.sb.support.redis.cache.resolver.impl;

import com.linfengda.sb.support.redis.cache.entity.bo.CacheResultBO;
import com.linfengda.sb.support.redis.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.redis.cache.entity.dto.HashKey;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import com.linfengda.sb.support.redis.cache.resolver.AbstractCacheDataTypeResolver;
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
public class HashCacheDataTypeResolver extends AbstractCacheDataTypeResolver {

    @Override
    public boolean support(DataType dataType) {
        return DataType.HASH == dataType;
    }

    @Override
    public CacheResultBO doGetCache(CacheParamDTO param) {
        HashKey hashKey = param.getHashKey();
        Object value = genericRedisTemplate.hashGet(hashKey.getKey(), hashKey.getHashKey());
        CacheResultBO cacheResultBO = new CacheResultBO();
        if (null == value) {
            cacheResultBO.setHasKey(hasKey(param));
        }
        cacheResultBO.setTarget(value);
        return cacheResultBO;
    }

    @Override
    public void doSetCache(CacheParamDTO param, Object value) {
        HashKey hashKey = param.getHashKey();
        genericRedisTemplate.hashPut(hashKey.getKey(), hashKey.getHashKey(), value, param.getTimeOutMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void delCache(CacheParamDTO param) {
        HashKey hashKey = param.getHashKey();
        Boolean allEntries = param.getDeleteMeta().getAllEntries();
        if (Boolean.FALSE.equals(allEntries)) {
            genericRedisTemplate.hashDel(hashKey.getKey(), hashKey.getHashKey());
            return;
        }
        genericRedisTemplate.delete(hashKey.getKey());
    }

    @Override
    public boolean hasKey(CacheParamDTO param) {
        HashKey hashKey = param.getHashKey();
        return genericRedisTemplate.hasHashKey(hashKey.getKey(), hashKey.getHashKey());
    }

    @Override
    public Long getCurrentCacheSize(CacheParamDTO param) {
        Set<String> set = genericRedisTemplate.hashKeys(param.getHashKey().getKey());
        if (CollectionUtils.isEmpty(set)) {
            return 0L;
        }
        return (long) set.size();
    }
}
