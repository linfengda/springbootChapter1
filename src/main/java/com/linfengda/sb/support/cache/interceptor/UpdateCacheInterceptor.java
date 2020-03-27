package com.linfengda.sb.support.cache.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 描述: 更新缓存注解拦截器
 *
 * @author linfengda
 * @create 2020-03-26 12:30
 */
@Slf4j
public class UpdateCacheInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("更新缓存方法执行，{}，参数：{}", invocation.getMethod().getName(), invocation.getArguments());
        return invocation.proceed();
    }
}
