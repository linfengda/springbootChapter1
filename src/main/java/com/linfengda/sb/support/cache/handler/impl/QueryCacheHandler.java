package com.linfengda.sb.support.cache.handler.impl;

import com.linfengda.sb.support.cache.handler.strategy.CacheStrategy;
import com.linfengda.sb.support.cache.entity.dto.CacheDataDTO;
import com.linfengda.sb.support.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.cache.entity.type.DataType;
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
        CacheParamDTO cacheParamDTO = getCacheDataDTO().getParam();
        MethodInvocation invocation = cacheParamDTO.getInvocation();
        DataType dataType = cacheParamDTO.getDataType();
        CacheStrategy strategy = dataType.getStrategy();
        Object value = strategy.getCache(cacheParamDTO);
        if (null != value) {
            return value;
        }
        value = invocation.proceed();
        strategy.setCache(cacheParamDTO, value);
        return value;
    }
}
