package com.linfengda.sb.support.cache.interceptor;

import com.linfengda.sb.support.cache.entity.meta.CacheMethodMeta;
import com.linfengda.sb.support.cache.parser.CacheAnnotationParser;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

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
        Method method = invocation.getMethod();
        if (!CacheAnnotationParser.hasCacheAnnotation(method)) {
            return invocation.proceed();
        }
        Object[] arguments = invocation.getArguments();




        CacheMethodMeta cacheMethodMeta = new CacheMethodMeta();
        cacheMethodMeta.setMethod(method);
        cacheMethodMeta.setMethodName(method.getName());
        cacheMethodMeta.setReturnType(method.getReturnType());

        String prefix = null;

        cacheMethodMeta.setMethod(method);
        cacheMethodMeta.setMethod(method);
        cacheMethodMeta.setMethod(method);
        cacheMethodMeta.setMethod(method);
        cacheMethodMeta.setMethod(method);



        Parameter[] parameters = invocation.getMethod().getParameters();
        for (Parameter parameter : parameters) {
            //invocation.getMethod().getAnnotation()
        }
        log.info("查询缓存方法执行，{}，参数：{}", invocation.getMethod().getName(), invocation.getArguments());
        return invocation.proceed();
    }

}
