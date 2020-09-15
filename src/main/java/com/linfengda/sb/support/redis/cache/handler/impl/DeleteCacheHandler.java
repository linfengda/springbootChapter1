package com.linfengda.sb.support.redis.cache.handler.impl;

import com.linfengda.sb.support.redis.cache.entity.dto.CacheTargetDTO;
import com.linfengda.sb.support.redis.cache.entity.type.CacheAnnotationType;
import com.linfengda.sb.support.redis.cache.handler.AbstractCacheHandler;
import com.linfengda.sb.support.redis.cache.resolver.CacheDataTypeResolver;
import com.linfengda.sb.support.redis.cache.resolver.CacheDataTypeResolverHolder;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 描述: 删除缓存注解处理 handler
 *
 * @author: linfengda
 * @date: 2020-07-07 11:00
 */
@Slf4j
public class DeleteCacheHandler extends AbstractCacheHandler {

    @Override
    public boolean support(CacheAnnotationType annotationType) {
        return CacheAnnotationType.DELETE == annotationType;
    }

    @Override
    public Object doCache(CacheTargetDTO cacheTargetDTO) throws Throwable {
        log.debug("删除缓存注解处理，dataType={}", cacheTargetDTO.getParam().getDataType());
        CacheDataTypeResolver resolver = CacheDataTypeResolverHolder.INSTANCE.getResolver(cacheTargetDTO.getParam().getDataType());
        resolver.delCache(cacheTargetDTO.getParam());
        MethodInvocation invocation = cacheTargetDTO.getInvocation();
        return invocation.proceed();
    }
}
