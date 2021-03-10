package com.lfd.soa.srv.demo.support.redis.cache.resolver.impl;

import com.lfd.soa.srv.demo.support.redis.Constant;
import com.lfd.soa.srv.demo.support.redis.cache.entity.bo.CacheResultBO;
import com.lfd.soa.srv.demo.support.redis.cache.entity.dto.CacheParamDTO;
import com.lfd.soa.srv.demo.support.redis.cache.entity.type.DataType;
import com.lfd.soa.srv.demo.support.redis.cache.resolver.AbstractCacheDataTypeResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 描述: set
 *
 * @author linfengda
 * @date 2020-08-01 16:23
 */
@Slf4j
public class SetCacheDataTypeResolver extends AbstractCacheDataTypeResolver {

    @Override
    public boolean support(DataType dataType) {
        return DataType.SET == dataType;
    }

    @Override
    public CacheResultBO doGetCache(CacheParamDTO param) {
        Object value = genericRedisTemplate.setGetAll(param.getKey());
        CacheResultBO resultBO = new CacheResultBO();
        Set set = (Set) value;
        if (CollectionUtils.isEmpty(set)) {
            resultBO.setHasKey(hasKey(param));
        }else {
            resultBO.setHasKey(true);
        }
        resultBO.setTarget(value);
        return resultBO;
    }

    @Override
    public void doSetCache(CacheParamDTO param, Object value) {
        if (value instanceof List) {
            List list = (List) value;
            genericRedisTemplate.setAddAll(param.getKey(), list, param.getTimeOutMillis(), TimeUnit.MILLISECONDS);
        }else if (value instanceof Set) {
            Set set = (Set) value;
            genericRedisTemplate.setAddAll(param.getKey(), set, param.getTimeOutMillis(), TimeUnit.MILLISECONDS);
        }else {
            genericRedisTemplate.setAdd(param.getKey(), value, param.getTimeOutMillis(), TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void delCache(CacheParamDTO param) {
        if (param.getAllEntries()) {
            delAllEntries(param);
            return;
        }
        genericRedisTemplate.delete(param.getKey());
    }

    @Override
    public boolean hasKey(CacheParamDTO param) {
        return genericRedisTemplate.hasKey(param.getKey());
    }

    @Override
    public boolean hasSize(CacheParamDTO param) {
        // 获取指定类型redis集合数量，如：mySet:{*}大小
        String keyPattern = param.getPrefix() + Constant.ASTERISK;
        Set<String> set = genericRedisTemplate.keys(keyPattern);
        if (CollectionUtils.isEmpty(set)) {
            return true;
        }
        return set.size() < param.getMaxSize();
    }
}
