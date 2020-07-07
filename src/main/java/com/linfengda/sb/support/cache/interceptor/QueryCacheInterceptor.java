package com.linfengda.sb.support.cache.interceptor;

import com.linfengda.sb.support.cache.entity.type.OperationType;
import com.linfengda.sb.support.cache.handler.CacheRouter;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 描述: 查询缓存注解拦截器
 *
 * @author linfengda
 * @create 2020-03-24 15:16
 */
@Slf4j
public class QueryCacheInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("查询缓存注解拦截，{}，参数：{}", invocation.getMethod().getName(), invocation.getArguments());
        return CacheRouter.INSTANCE.doCache(invocation, OperationType.QUERY);
    }
}