package com.linfengda.sb.support.redis.cache.handler.impl;

import com.linfengda.sb.support.redis.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.redis.cache.entity.dto.CacheTargetDTO;
import com.linfengda.sb.support.redis.cache.entity.type.CacheAnnotationType;
import com.linfengda.sb.support.redis.cache.handler.AbstractCacheHandler;
import com.linfengda.sb.support.redis.cache.resolver.CacheDataTypeResolver;
import com.linfengda.sb.support.redis.cache.resolver.CacheDataTypeResolverHolder;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 描述: 更新缓存注解处理 handler
 *
 * @author: linfengda
 * @date: 2020-07-07 11:00
 */
@Slf4j
public class UpdateCacheHandler extends AbstractCacheHandler {

    @Override
    public boolean support(CacheAnnotationType annotationType) {
        return CacheAnnotationType.UPDATE == annotationType;
    }

    @Override
    public Object doCache(CacheTargetDTO cacheTargetDTO) throws Throwable {
        log.debug("更新缓存注解处理，dataType={}", cacheTargetDTO.getParam().getDataType());
        MethodInvocation invocation = cacheTargetDTO.getInvocation();
        Object value = invocation.proceed();
        CacheParamDTO param = cacheTargetDTO.getParam();
        CacheDataTypeResolver resolver = CacheDataTypeResolverHolder.INSTANCE.getResolver(param.getDataType());
        resolver.setCache(param, value);
        return value;
    }
}
