package com.linfengda.sb.support.middleware.redis.cache.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 描述: 缓存方法拦截器
 *
 * @author linfengda
 * @create 2020-03-24 15:16
 */
@Slf4j
public class BizQueryCacheInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("缓存的业务查询方法执行，{}，参数：{}", invocation.getMethod().getName(), invocation.getArguments());
        return invocation.proceed();
    }
}
