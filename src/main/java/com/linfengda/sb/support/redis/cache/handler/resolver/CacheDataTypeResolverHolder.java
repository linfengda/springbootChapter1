package com.linfengda.sb.support.redis.cache.handler.resolver;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import com.linfengda.sb.support.redis.cache.handler.RedisSupport;
import com.linfengda.sb.support.redis.cache.handler.resolver.impl.HashCacheDataTypeResolver;
import com.linfengda.sb.support.redis.cache.handler.resolver.impl.ListCacheDataTypeResolver;
import com.linfengda.sb.support.redis.cache.handler.resolver.impl.ObjCacheDataTypeResolver;
import com.linfengda.sb.support.redis.cache.handler.resolver.impl.SetCacheDataTypeResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 初始化全部resolver
 * @author: linfengda
 * @date: 2020-08-16 16:36
 */
public enum CacheDataTypeResolverHolder {
    /**
     * 单例
     */
    INSTANCE;

    private final List<CacheDataTypeResolver> resolvers = new ArrayList<>();

    /**
     * 初始化全部resolver
     * @param redisSupport
     */
    public void initResolver(RedisSupport redisSupport) {
        HashCacheDataTypeResolver hashCacheDataTypeResolver = new HashCacheDataTypeResolver();
        hashCacheDataTypeResolver.setJacksonRedisTemplate(redisSupport.getJacksonRedisTemplate());
        ListCacheDataTypeResolver listCacheDataTypeResolver = new ListCacheDataTypeResolver();
        listCacheDataTypeResolver.setJacksonRedisTemplate(redisSupport.getJacksonRedisTemplate());
        ObjCacheDataTypeResolver objCacheDataTypeResolver = new ObjCacheDataTypeResolver();
        objCacheDataTypeResolver.setJacksonRedisTemplate(redisSupport.getJacksonRedisTemplate());
        SetCacheDataTypeResolver setCacheDataTypeResolver = new SetCacheDataTypeResolver();
        setCacheDataTypeResolver.setJacksonRedisTemplate(redisSupport.getJacksonRedisTemplate());
        resolvers.add(hashCacheDataTypeResolver);
        resolvers.add(listCacheDataTypeResolver);
        resolvers.add(objCacheDataTypeResolver);
        resolvers.add(setCacheDataTypeResolver);
    }

    /**
     * 根据数据类型获取resolver
     * @param dataType
     * @return
     */
    public CacheDataTypeResolver getResolver(DataType dataType) {
        for (CacheDataTypeResolver resolver : resolvers) {
            if (resolver.support(dataType)) {
                return resolver;
            }
        }
        throw new BusinessException("不支持的数据类型！");
    }
}
