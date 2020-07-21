package com.linfengda.sb.support.cache.handler.impl;

import com.linfengda.sb.support.cache.config.RedisSupportHolder;
import com.linfengda.sb.support.cache.entity.dto.CacheDataDTO;
import com.linfengda.sb.support.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.cache.entity.type.CacheExtraStrategy;
import com.linfengda.sb.support.cache.handler.strategy.CacheStrategy;
import com.linfengda.sb.support.cache.redis.lock.RedisDistributedLock;
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
        log.debug("查询缓存注解处理 handler，dataType={}", getCacheDataDTO().getParam().getDataType());
        CacheParamDTO param = getCacheDataDTO().getParam();
        CacheStrategy strategy = param.getDataType().getStrategy();
        Object value = strategy.getCache(param);
        if (null != value) {
            return value;
        }
        if (!param.getStrategies().contains(CacheExtraStrategy.NO_CACHE_HOT_KEY_MULTI_LOAD)) {
            return getMethodResult(param);
        }
        RedisDistributedLock distributedLock = RedisSupportHolder.getRedisDistributedLock();
        try {
            if (distributedLock.lock(param.getLockKey())) {
                value = strategy.getCache(param);
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
        CacheStrategy strategy = param.getDataType().getStrategy();
        MethodInvocation invocation = getCacheDataDTO().getInvocation();
        Object value = invocation.proceed();
        strategy.setCache(param, value);
        return value;
    }
}
