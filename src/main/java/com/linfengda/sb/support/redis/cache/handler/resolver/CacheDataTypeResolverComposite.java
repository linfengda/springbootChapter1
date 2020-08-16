package com.linfengda.sb.support.redis.cache.handler.resolver;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.support.redis.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import com.linfengda.sb.support.redis.cache.handler.RedisSupport;
import com.linfengda.sb.support.redis.cache.handler.resolver.impl.HashCacheDataTypeResolver;
import com.linfengda.sb.support.redis.cache.handler.resolver.impl.ListCacheDataTypeResolver;
import com.linfengda.sb.support.redis.cache.handler.resolver.impl.ObjCacheDataTypeResolver;
import com.linfengda.sb.support.redis.cache.handler.resolver.impl.SetCacheDataTypeResolver;

import java.util.List;

/**
 * @description:
 * @author: linfengda
 * @date: 2020-08-16 16:36
 */
public class CacheDataTypeResolverComposite extends AbstractCacheDataTypeResolver {
    private List<CacheDataTypeResolver> resolvers;

    public void init(RedisSupport redisSupport) {
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

    public CacheDataTypeResolver getResolver(DataType dataType) {
        for (CacheDataTypeResolver resolver : resolvers) {
            if (resolver.support(dataType)) {
                return resolver;
            }
        }
        throw new BusinessException("不支持的数据类型！");
    }


    @Override
    public Object doGetCache(CacheParamDTO param) {
        return getResolver(param.getDataType()).getCache(param);
    }

    @Override
    public void doSetCache(CacheParamDTO param, Object value) {
        getResolver(param.getDataType()).setCache(param, value);
    }

    @Override
    public Long getCurrentCacheSize(CacheParamDTO param) {
        return getResolver(param.getDataType()).getCurrentCacheSize(param);
    }

    @Override
    public Boolean hasKey(CacheParamDTO param) {
        return getResolver(param.getDataType()).hasKey(param);
    }

    @Override
    public boolean support(DataType dataType) {
        return true;
    }
}
