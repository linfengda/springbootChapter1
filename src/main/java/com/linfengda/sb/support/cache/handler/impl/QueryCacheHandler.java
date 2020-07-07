package com.linfengda.sb.support.cache.handler.impl;

import com.linfengda.sb.support.cache.entity.dto.CacheDataDTO;
import com.linfengda.sb.support.cache.entity.meta.CacheMethodMeta;
import com.linfengda.sb.support.cache.entity.type.CacheDataStrategy;
import com.linfengda.sb.support.cache.entity.type.DataType;
import com.linfengda.sb.support.cache.exception.BusinessException;
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
        CacheMethodMeta cacheMethodMeta = getCacheDataDTO().getMeta();
        MethodInvocation invocation = cacheMethodMeta.getInvocation();
        DataType dataType = cacheMethodMeta.getDataType();

        CacheDataStrategy strategy = CacheDataStrategy.getStrategy(dataType);
        if (null == strategy) {
            throw new BusinessException("不支持的缓存数据类型，支持的类型有：" + DataType.values());
        }
        Object value = strategy.getCache(cacheMethodMeta);
        if (null != value) {
            return value;
        }
        value = invocation.proceed();
        strategy.setCache(cacheMethodMeta, value);
        return value;
    }
}
