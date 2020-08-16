package com.linfengda.sb.support.redis.cache.handler.resolver.impl;

import com.linfengda.sb.support.redis.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import com.linfengda.sb.support.redis.cache.handler.resolver.AbstractCacheDataTypeResolver;
import com.linfengda.sb.support.redis.Constant;
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
public class ObjCacheDataTypeResolver extends AbstractCacheDataTypeResolver {

    @Override
    public boolean support(DataType dataType) {
        return DataType.OBJECT == dataType;
    }

    @Override
    public Object doGetCache(CacheParamDTO param) {
        Object value = jacksonRedisTemplate.getObject(param.getKey());
        return value;
    }

    @Override
    public void doSetCache(CacheParamDTO param, Object value) {
        jacksonRedisTemplate.setObject(param.getKey(), value, param.getTimeOutMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public Boolean hasKey(CacheParamDTO param) {
        String key = param.getKey();
        return jacksonRedisTemplate.hasKey(key);
    }

    @Override
    public Long getCurrentCacheSize(CacheParamDTO param) {
        String keyPattern = param.getPrefix() + Constant.ASTERISK;
        Set<String> set = jacksonRedisTemplate.keys(keyPattern);
        if (CollectionUtils.isEmpty(set)) {
            return 0L;
        }
        return (long) set.size();
    }
}
