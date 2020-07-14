package com.linfengda.sb.support.cache.handler;

import com.linfengda.sb.support.cache.builder.CacheMethodMetaBuilder;
import com.linfengda.sb.support.cache.builder.CacheParamBuilder;
import com.linfengda.sb.support.cache.entity.dto.CacheDataDTO;
import com.linfengda.sb.support.cache.entity.meta.CacheMethodMeta;
import com.linfengda.sb.support.cache.entity.type.OperationType;
import com.linfengda.sb.support.cache.manager.CacheHandlerManager;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * 描述: 缓存路由（使用委派模式设计）
 *
 * @author linfengda
 * @create 2019-12-19 17:52
 */
@Slf4j
public enum CacheRouter {
    /**
     * 路由
     */
    INSTANCE;

    public Object doCache(MethodInvocation invocation, OperationType operationType) throws Throwable {
        Method method = invocation.getMethod();
        if (!CacheMethodMetaBuilder.checkCacheAnnotation(method)) {
            return invocation.proceed();
        }
        CacheMethodMeta cacheMethodMeta = CacheMethodMetaBuilder.getCacheMethodMeta(method);
        Object[] arguments = invocation.getArguments();

        CacheDataDTO cacheDataDTO = new CacheDataDTO();
        cacheDataDTO.setInvocation(invocation);
        cacheDataDTO.setType(operationType);
        cacheDataDTO.setParam(CacheParamBuilder.INSTANCE.initCacheParam(cacheMethodMeta, arguments));
        CacheHandler handler = CacheHandlerManager.provide(cacheDataDTO);
        return handler.doCache();
    }
}
