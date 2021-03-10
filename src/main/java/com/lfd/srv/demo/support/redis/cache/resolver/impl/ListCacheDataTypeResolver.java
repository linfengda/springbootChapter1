package com.lfd.srv.demo.support.redis.cache.resolver.impl;

import com.lfd.srv.demo.support.redis.Constant;
import com.lfd.srv.demo.support.redis.cache.entity.bo.CacheResultBO;
import com.lfd.srv.demo.support.redis.cache.entity.dto.CacheParamDTO;
import com.lfd.srv.demo.support.redis.cache.entity.type.DataType;
import com.lfd.srv.demo.support.redis.cache.resolver.AbstractCacheDataTypeResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 描述: list
 *
 * @author linfengda
 * @date 2020-08-01 16:22
 */
@Slf4j
public class ListCacheDataTypeResolver extends AbstractCacheDataTypeResolver {

    @Override
    public boolean support(DataType dataType) {
        return DataType.LIST == dataType;
    }

    @Override
    public CacheResultBO doGetCache(CacheParamDTO param) {
        Object value = genericRedisTemplate.listGetAll(param.getKey());
        CacheResultBO resultBO = new CacheResultBO();
        List list = (List) value;
        if (CollectionUtils.isEmpty(list)) {
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
            genericRedisTemplate.listAddAll(param.getKey(), list, param.getTimeOutMillis(), TimeUnit.MILLISECONDS);
        }else if (value instanceof Set) {
            Set set = (Set) value;
            genericRedisTemplate.listAddAll(param.getKey(), set, param.getTimeOutMillis(), TimeUnit.MILLISECONDS);
        }else {
            genericRedisTemplate.listAdd(param.getKey(), value, param.getTimeOutMillis(), TimeUnit.MILLISECONDS);
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
        // 获取指定类型redis列表数量，如：myList:{*}大小
        String keyPattern = param.getPrefix() + Constant.ASTERISK;
        Set<String> set = genericRedisTemplate.keys(keyPattern);
        if (CollectionUtils.isEmpty(set)) {
            return true;
        }
        return set.size() < param.getMaxSize();
    }
}
