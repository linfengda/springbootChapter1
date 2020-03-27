package com.linfengda.sb.support.cache.interceptor;

import com.linfengda.sb.support.cache.annotation.MapCacheDelete;
import com.linfengda.sb.support.cache.annotation.MapCacheUpdate;
import com.linfengda.sb.support.cache.annotation.ObjCacheDelete;
import com.linfengda.sb.support.cache.annotation.ObjCacheUpdate;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 描述: 更新缓存注解静态切入点
 *
 * @author linfengda
 * @create 2020-03-26 11:32
 */
public class UpdateCacheMethodPointcut extends StaticMethodMatcherPointcut {
    /**
     * 更新缓存注解列表
     */
    private static final Class<? extends Annotation>[] CACHE_ANNOTATION_CLASSES = new Class[] {ObjCacheUpdate.class, ObjCacheDelete.class, MapCacheUpdate.class, MapCacheDelete.class};


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
