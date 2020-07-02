package com.linfengda.sb.support.cache.interceptor;

import com.linfengda.sb.chapter1.common.util.SpringUtil;
import com.linfengda.sb.support.cache.entity.meta.CacheMethodMeta;
import com.linfengda.sb.support.cache.parser.CacheAnnotationParser;
import com.linfengda.sb.support.middleware.redis.template.JacksonRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * 描述: 查询缓存注解拦截器
 *
 * @author linfengda
 * @create 2020-03-24 15:16
 */
@Slf4j
public class QueryCacheInterceptor extends CacheAnnotationParser implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        if (!hasCacheAnnotation(method)) {
            return invocation.proceed();
        }
        CacheMethodMeta cacheMethodMeta = getCacheAnnotation(method);
        // 进行缓存
        JacksonRedisTemplate jacksonRedisTemplate = SpringUtil.getBean(JacksonRedisTemplate.class);


        log.info("查询缓存注解拦截，{}，参数：{}", invocation.getMethod().getName(), invocation.getArguments());
        return invocation.proceed();
    }

}
