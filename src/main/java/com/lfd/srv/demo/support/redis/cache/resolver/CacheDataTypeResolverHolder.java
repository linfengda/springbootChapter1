package com.lfd.srv.demo.support.redis.cache.resolver;

import com.lfd.srv.demo.support.redis.cache.resolver.impl.HashCacheDataTypeResolver;
import com.lfd.srv.demo.support.redis.cache.resolver.impl.ListCacheDataTypeResolver;
import com.lfd.srv.demo.support.redis.cache.resolver.impl.ObjCacheDataTypeResolver;
import com.lfd.srv.demo.support.redis.cache.resolver.impl.SetCacheDataTypeResolver;
import com.lfd.common.exception.BusinessException;
import com.lfd.srv.demo.support.redis.GenericRedisTemplate;
import com.lfd.srv.demo.support.redis.cache.entity.type.DataType;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 初始化全部resolver
 * @author linfengda
 * @date 2020-08-01 16:36
 */
public enum CacheDataTypeResolverHolder {
    /**
     * 单例
     */
    INSTANCE;
    private final List<CacheDataTypeResolver> resolvers = new ArrayList<>();

    /**
     * 初始化全部resolver
     * @param genericRedisTemplate
     */
    public void initResolver(GenericRedisTemplate genericRedisTemplate) {
        HashCacheDataTypeResolver hashCacheDataTypeResolver = new HashCacheDataTypeResolver();
        hashCacheDataTypeResolver.setGenericRedisTemplate(genericRedisTemplate);
        ListCacheDataTypeResolver listCacheDataTypeResolver = new ListCacheDataTypeResolver();
        listCacheDataTypeResolver.setGenericRedisTemplate(genericRedisTemplate);
        ObjCacheDataTypeResolver objCacheDataTypeResolver = new ObjCacheDataTypeResolver();
        objCacheDataTypeResolver.setGenericRedisTemplate(genericRedisTemplate);
        SetCacheDataTypeResolver setCacheDataTypeResolver = new SetCacheDataTypeResolver();
        setCacheDataTypeResolver.setGenericRedisTemplate(genericRedisTemplate);
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
