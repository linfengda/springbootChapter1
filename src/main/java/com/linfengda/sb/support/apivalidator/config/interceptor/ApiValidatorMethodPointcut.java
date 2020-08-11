package com.linfengda.sb.support.apivalidator.config.interceptor;

import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 描述: 匹配{@link RestController}的静态切入点
 *
 * @author linfengda
 * @create 2020-03-24 15:59
 */
public class ApiValidatorMethodPointcut extends StaticMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, Class<?> clazz) {
        Annotation annotation = AnnotationUtils.findAnnotation(clazz, RestController.class);
        if (null == annotation) {
            return false;
        }
        return true;
    }
}
