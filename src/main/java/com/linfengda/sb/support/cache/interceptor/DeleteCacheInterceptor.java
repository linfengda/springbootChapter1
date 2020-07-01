package com.linfengda.sb.support.cache.interceptor;

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
        return null;
    }
}
