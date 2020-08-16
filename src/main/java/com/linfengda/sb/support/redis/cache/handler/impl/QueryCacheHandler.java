package com.linfengda.sb.support.redis.cache.handler.impl;

import com.linfengda.sb.support.redis.RedisDistributedLock;
import com.linfengda.sb.support.redis.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.redis.cache.entity.dto.CacheTargetDTO;
import com.linfengda.sb.support.redis.cache.entity.type.CacheExtraStrategy;
import com.linfengda.sb.support.redis.cache.handler.resolver.CacheDataTypeResolver;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @description: 查询缓存注解处理 handler
 * @author: linfengda
 * @date: 2020-06-17 14:08
 */
@Slf4j
public class QueryCacheHandler extends AbstractCacheHandler {

    public QueryCacheHandler(CacheTargetDTO cacheTargetDTO) {
        super(cacheTargetDTO);
    }

    @Override
    public Object doCache() throws Throwable {
        log.debug("查询缓存注解处理 handler，dataType={}", getCacheTargetDTO().getParam().getDataType());
        CacheParamDTO param = getCacheTargetDTO().getParam();
        CacheDataTypeResolver resolver = getCacheDataTypeResolverComposite();
        Object value = resolver.getCache(param);
        if (null != value) {
            return value;
        }
        if (!param.getStrategies().contains(CacheExtraStrategy.NO_CACHE_HOT_KEY_MULTI_LOAD)) {
            return getMethodResult(param);
        }
        RedisDistributedLock distributedLock = getRedisSupport().getRedisDistributedLock();
        try {
            if (distributedLock.lock(param.getLockKey())) {
                value = resolver.getCache(param);
                if (null != value) {
                    return value;
                }
                return getMethodResult(param);
            }
            log.warn("缓存查询超时，将会直接查询DB。");
        }finally {
            distributedLock.unLock(param.getLockKey());
        }
        return getMethodResult(param);
    }

    /**
     * 执行方法
     * @param param     参数
     * @return          数据
     * @throws Throwable
     */
    private Object getMethodResult(CacheParamDTO param) throws Throwable {
        MethodInvocation invocation = getCacheTargetDTO().getInvocation();
        Object value = invocation.proceed();
        getCacheDataTypeResolverComposite().setCache(param, value);
        return value;
    }
}
