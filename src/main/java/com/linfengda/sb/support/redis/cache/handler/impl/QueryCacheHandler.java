package com.linfengda.sb.support.redis.cache.handler.impl;

import com.linfengda.sb.support.redis.RedisDistributedLock;
import com.linfengda.sb.support.redis.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.redis.cache.entity.dto.CacheTargetDTO;
import com.linfengda.sb.support.redis.cache.entity.type.CacheAnnotationType;
import com.linfengda.sb.support.redis.cache.entity.type.CacheExtraStrategy;
import com.linfengda.sb.support.redis.cache.handler.AbstractCacheHandler;
import com.linfengda.sb.support.redis.cache.resolver.CacheDataTypeResolver;
import com.linfengda.sb.support.redis.cache.resolver.CacheDataTypeResolverHolder;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @description: 查询缓存注解处理 handler
 * @author: linfengda
 * @date: 2020-06-17 14:08
 */
@Slf4j
public class QueryCacheHandler extends AbstractCacheHandler {

    @Override
    public boolean support(CacheAnnotationType annotationType) {
        return CacheAnnotationType.QUERY == annotationType;
    }

    @Override
    public Object doCache(CacheTargetDTO cacheTargetDTO) throws Throwable {
        log.debug("查询缓存注解处理 handler，dataType={}", cacheTargetDTO.getParam().getDataType());
        CacheParamDTO param = cacheTargetDTO.getParam();
        CacheDataTypeResolver resolver = CacheDataTypeResolverHolder.INSTANCE.getResolver(param.getDataType());
        Object value = resolver.getCache(param);
        if (null != value) {
            return value;
        }
        if (!param.getStrategies().contains(CacheExtraStrategy.PRV_CACHE_HOT_KEY_MULTI_LOAD)) {
            return getMethodResult(cacheTargetDTO);
        }
        RedisDistributedLock distributedLock = redisSupport.getRedisDistributedLock();
        try {
            if (distributedLock.lock(param.getLockKey())) {
                value = resolver.getCache(param);
                if (null != value) {
                    return value;
                }
                return getMethodResult(cacheTargetDTO);
            }
            log.warn("缓存查询超时，将会直接查询DB。");
        }finally {
            distributedLock.unLock(param.getLockKey());
        }
        return getMethodResult(cacheTargetDTO);
    }

    /**
     * 执行方法
     * @param cacheTargetDTO    对象
     * @return                  方法返回值
     * @throws Throwable
     */
    private Object getMethodResult(CacheTargetDTO cacheTargetDTO) throws Throwable {
        MethodInvocation invocation = cacheTargetDTO.getInvocation();
        Object value = invocation.proceed();
        CacheDataTypeResolver resolver = CacheDataTypeResolverHolder.INSTANCE.getResolver(cacheTargetDTO.getParam().getDataType());
        resolver.setCache(cacheTargetDTO.getParam(), value);
        return value;
    }
}
