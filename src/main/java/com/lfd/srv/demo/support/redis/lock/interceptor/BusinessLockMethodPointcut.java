package com.lfd.srv.demo.support.redis.lock.interceptor;

import com.lfd.srv.demo.support.redis.lock.annotation.BusinessLock;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 描述: 匹配{@link BusinessLock}的静态切入点
 *
 * @author linfengda
 * @create 2020-03-24 15:59
 */
public class BusinessLockMethodPointcut extends StaticMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, Class<?> clazz) {
        Annotation annotation = AnnotationUtils.findAnnotation(method, BusinessLock.class);
        if (null == annotation) {
            return false;
        }
        return true;
    }
}
