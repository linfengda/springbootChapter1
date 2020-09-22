package com.linfengda.sb.support.redis.cache.resolver.impl;

import com.linfengda.sb.support.redis.Constant;
import com.linfengda.sb.support.redis.cache.entity.bo.CacheResultBO;
import com.linfengda.sb.support.redis.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import com.linfengda.sb.support.redis.cache.resolver.AbstractCacheDataTypeResolver;
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
    public CacheResultBO doGetCache(CacheParamDTO param) {
        Object value = genericRedisTemplate.getObject(param.getKey());
        CacheResultBO resultBO = new CacheResultBO();
        if (null == value) {
            resultBO.setHasKey(hasKey(param));
        }else {
            resultBO.setHasKey(true);
        }
        resultBO.setTarget(value);
        return resultBO;
    }

    @Override
    public void doSetCache(CacheParamDTO param, Object value) {
        genericRedisTemplate.setObject(param.getKey(), value, param.getTimeOutMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void delCache(CacheParamDTO param) {
        Boolean allEntries = param.getAllEntries();
        if (Boolean.TRUE.equals(allEntries)) {
            delAllEntries(param);
            return;
        }
        genericRedisTemplate.delete(param.getKey());
    }

    @Override
    public boolean hasSize(CacheParamDTO param) {
        String keyPattern = param.getPrefix() + Constant.ASTERISK;
        Set<String> set = genericRedisTemplate.keys(keyPattern);
        if (CollectionUtils.isEmpty(set)) {
            return true;
        }
        return set.size() < param.getMaxSize();
    }

    @Override
    public boolean hasKey(CacheParamDTO param) {
        return genericRedisTemplate.hasKey(param.getKey());
    }
}
