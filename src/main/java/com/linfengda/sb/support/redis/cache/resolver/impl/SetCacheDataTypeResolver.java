package com.linfengda.sb.support.redis.cache.resolver.impl;

import com.linfengda.sb.support.redis.Constant;
import com.linfengda.sb.support.redis.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import com.linfengda.sb.support.redis.cache.resolver.AbstractCacheDataTypeResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 描述: set
 *
 * @author: linfengda
 * @date: 2020-07-08 16:23
 */
@Slf4j
public class SetCacheDataTypeResolver extends AbstractCacheDataTypeResolver {

    @Override
    public boolean support(DataType dataType) {
        return DataType.SET == dataType;
    }

    @Override
    public Object doGetCache(CacheParamDTO param) {
        // 获取redis集合，如：mySet:{id}
        Object value = jacksonRedisTemplate.listGetAll(param.getKey());
        return value;
    }

    @Override
    public void doSetCache(CacheParamDTO param, Object value) {
        if (value instanceof List) {
            List list = (List) value;
            jacksonRedisTemplate.setAddAll(param.getKey(), list, param.getTimeOutMillis(), TimeUnit.MILLISECONDS);
        }else if (value instanceof Set) {
            Set set = (Set) value;
            jacksonRedisTemplate.setAddAll(param.getKey(), set, param.getTimeOutMillis(), TimeUnit.MILLISECONDS);
        }else {
            jacksonRedisTemplate.setAdd(param.getKey(), value, param.getTimeOutMillis(), TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public Boolean hasKey(CacheParamDTO param) {
        return null;
    }

    @Override
    public Long getCurrentCacheSize(CacheParamDTO param) {
        // 获取指定类型redis集合数量大小，如：mySet:{*}大小
        String keyPattern = param.getPrefix() + Constant.ASTERISK;
        Set<String> set = jacksonRedisTemplate.keys(keyPattern);
        if (CollectionUtils.isEmpty(set)) {
            return 0L;
        }
        return (long) set.size();
    }
}
