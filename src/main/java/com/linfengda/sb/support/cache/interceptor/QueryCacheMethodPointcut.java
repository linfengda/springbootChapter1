package com.linfengda.sb.support.cache.interceptor;

import com.linfengda.sb.support.cache.manager.CacheAnnotationManager;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 描述: 查询缓存注解静态切入点
 *
 * @author linfengda
 * @create 2020-03-24 15:59
 */
public class QueryCacheMethodPointcut extends StaticMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, Class<?> aClass) {
        for (Class<? extends Annotation> annotationType : CacheAnnotationManager.QUERY_CACHE_ANNOTATIONS.getAnnotations()) {
            Annotation annotation = AnnotationUtils.findAnnotation(method, annotationType);
            if (null != annotation) {
                return true;
            }
        }
        return false;
    }
}
