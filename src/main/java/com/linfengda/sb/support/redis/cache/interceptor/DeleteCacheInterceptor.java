package com.linfengda.sb.support.redis.cache.interceptor;

import com.linfengda.sb.support.redis.cache.entity.type.CacheAnnotationType;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @description 拦截{@link com.linfengda.sb.support.redis.cache.annotation.DeleteCache}注解
 * @author linfengda
 * @date 2020-06-27 11:53
 */
@Slf4j
public class DeleteCacheInterceptor extends CacheMethodHandlerAdapter implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.debug("删除缓存注解拦截，{}，参数：{}", invocation.getMethod().getName(), invocation.getArguments());
        return super.invokeCacheMethod(invocation, CacheAnnotationType.DELETE);
    }
}
