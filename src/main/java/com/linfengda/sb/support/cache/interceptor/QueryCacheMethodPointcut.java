package com.linfengda.sb.support.cache.interceptor;

import com.linfengda.sb.support.cache.annotation.MapCache;
import com.linfengda.sb.support.cache.annotation.ObjCache;
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
    /**
     * 查询缓存注解列表
     */
    private static final Class<? extends Annotation>[] CACHE_ANNOTATION_CLASSES = new Class[] {ObjCache.class, MapCache.class};


    @Override
    public boolean matches(Method method, Class<?> aClass) {
        for (Class<? extends Annotation> cacheAnnotationClass : CACHE_ANNOTATION_CLASSES) {
            Annotation annotation = AnnotationUtils.findAnnotation(method, cacheAnnotationClass);
            if (null != annotation) {
                return true;
            }
        }
        return false;
    }
}
