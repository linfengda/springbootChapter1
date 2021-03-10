package com.lfd.soa.srv.demo.support.redis.cache.interceptor;

import com.lfd.soa.srv.demo.support.redis.cache.entity.type.CacheAnnotationType;
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
    public boolean matches(Method method, Class<?> clazz) {
        Annotation annotation = AnnotationUtils.findAnnotation(method, CacheAnnotationType.QUERY.getAnnotation());
        if (null == annotation) {
            return false;
        }
        return true;
    }
}
