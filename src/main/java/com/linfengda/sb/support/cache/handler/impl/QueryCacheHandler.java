package com.linfengda.sb.support.cache.handler.impl;

import com.linfengda.sb.support.cache.builder.CacheKeyBuilder;
import com.linfengda.sb.support.cache.builder.HashKey;
import com.linfengda.sb.support.cache.entity.dto.CacheDataDTO;
import com.linfengda.sb.support.cache.entity.meta.CacheMethodMeta;
import com.linfengda.sb.support.cache.entity.type.DataType;
import com.linfengda.sb.support.middleware.redis.template.JacksonRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @description: 查询缓存注解处理 handler
 * @author: linfengda
 * @date: 2020-06-17 14:08
 */
@Slf4j
public class QueryCacheHandler extends AbstractCacheHandler {

    public QueryCacheHandler(CacheDataDTO cacheDataDTO) {
        super(cacheDataDTO);
    }

    @Override
    public Object doCache() throws Throwable {
        log.debug("查询缓存注解处理 handler，dataType={}", getCacheDataDTO().getMeta().getDataType());
        JacksonRedisTemplate jacksonRedisTemplate = getRedisTemplate();
        CacheMethodMeta cacheMethodMeta = getCacheDataDTO().getMeta();
        MethodInvocation invocation = cacheMethodMeta.getInvocation();
        DataType dataType = cacheMethodMeta.getDataType();
        Class<?> returnType = cacheMethodMeta.getReturnType();
        if (DataType.OBJECT == dataType) {
            String key = CacheKeyBuilder.INSTANCE.buildObjectKey(cacheMethodMeta);
            Object value = jacksonRedisTemplate.getObject(key, returnType);
            if (null == value) {
                value = invocation.proceed();
                jacksonRedisTemplate.setObject(key, value);
            }
            return value;
        }else if (DataType.HASH == dataType) {
            HashKey hashKey = CacheKeyBuilder.INSTANCE.buildHashKey(cacheMethodMeta);
            Object value = jacksonRedisTemplate.mapGet(hashKey.getMapName(), hashKey.getHashKey(), returnType);
            if (null == value) {
                value = invocation.proceed();
                jacksonRedisTemplate.mapPut(hashKey.getMapName(), hashKey.getHashKey(), value);
            }
            return value;
        }else if (DataType.LIST == dataType) {
            String key = CacheKeyBuilder.INSTANCE.buildObjectKey(cacheMethodMeta);
            Object value = jacksonRedisTemplate.listGet(key, returnType);
            if (null == value) {
                value = invocation.proceed();
                //jacksonRedisTemplate.listAddAll(key, value);
            }
            return value;
        }
        return null;
    }
}
