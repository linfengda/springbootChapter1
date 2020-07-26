package com.linfengda.sb.support.redis.cache.interceptor;

import com.linfengda.sb.support.redis.cache.entity.type.OperationType;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @description: 更新缓存注解静态切入点
 * @author: linfengda
 * @date: 2020-06-27 11:20
 */
public class UpdateCacheMethodPointcut extends StaticMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, Class<?> clazz) {
        Annotation annotation = AnnotationUtils.findAnnotation(method, OperationType.UPDATE.getAnnotation());
        if (null == annotation) {
            return false;
        }
        return true;
    }
}
