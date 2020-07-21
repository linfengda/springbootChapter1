package com.linfengda.sb.support.cache.interceptor;

import com.linfengda.sb.support.cache.entity.type.OperationType;
import com.linfengda.sb.support.cache.handler.CacheRouter;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @description: 删除缓存注解拦截器
 * @author: linfengda
 * @date: 2020-06-27 11:53
 */
@Slf4j
public class DeleteCacheInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.debug("删除缓存注解拦截，{}，参数：{}", invocation.getMethod().getName(), invocation.getArguments());
        return CacheRouter.INSTANCE.doCache(invocation, OperationType.DELETE);
    }
}
