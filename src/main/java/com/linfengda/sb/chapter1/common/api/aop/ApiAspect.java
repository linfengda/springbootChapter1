package com.linfengda.sb.chapter1.common.api.aop;

import com.linfengda.sb.chapter1.common.api.context.RequestParamContext;
import com.linfengda.sb.chapter1.common.api.parameter.MyValidateUtils;
import com.linfengda.sb.chapter1.common.api.parameter.validate.BaseValidateParameterType;
import com.linfengda.sb.chapter1.common.api.parameter.validate.BeanValidateAnnotationType;
import com.linfengda.sb.chapter1.common.api.parameter.validate.FieldValidateAnnotationType;
import com.linfengda.sb.chapter1.common.api.parameter.validate.NotValidateParameterType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 描述: HTTP请求AOP
 *
 * @author linfengda
 * @create 2018-08-19 23:10
 */
@Aspect
@Component
@Slf4j
public class ApiAspect extends ApplicationObjectSupport {

    @Pointcut("execution(public * com.linfengda.sb..api..*(..))")
    public void authPoint() {

    }

    @Around("authPoint()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            Object[] args = checkControllerMethodParameter(proceedingJoinPoint);
            Object result = proceedingJoinPoint.proceed(args);
            return result;
        } finally {
            releaseSource();
        }
    }

    /**
     * 入参校验
     * @return
     */
    private Object[] checkControllerMethodParameter(ProceedingJoinPoint proceedingJoinPoint) {
        Object [] args = proceedingJoinPoint.getArgs();
        if (args == null || args.length == 0) {
            return args;
        }

        boolean needValidateBaseType = false;
        Method targetMethod = getMethodParameters(proceedingJoinPoint);
        Parameter[] parameters = targetMethod.getParameters();
        for (int i=0; i <parameters.length; i++){
            Parameter parameter = parameters[i];
            if (NotValidateParameterType.isNotValidateParameterType(parameter.getType().getName())) {
                continue;
            }
            if (BaseValidateParameterType.isBaseValidateParameterType(parameter.getType().getName())) {
                // 校验基本数据类型
                Annotation[] annotations = parameter.getAnnotations();
                if (isFieldValidateAnnotationType(annotations)) {
                    needValidateBaseType = true;
                    continue;
                }
            }else {
                // 校验Bean
                Annotation[] annotations = parameter.getAnnotations();
                if (isBeanValidateAnnotationType(annotations)) {
                    MyValidateUtils.validate(args[i]);
                }
            }
        }
        if (needValidateBaseType) {
            MyValidateUtils.validateParameters(proceedingJoinPoint.getTarget(), targetMethod, args);
        }
        return args;
    }

    private boolean isFieldValidateAnnotationType(Annotation[] annotations) {
        if (annotations != null && annotations.length > 0) {
            for (Annotation annotation : annotations) {
                if (FieldValidateAnnotationType.isValidateAnnotation(annotation.annotationType().getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isBeanValidateAnnotationType(Annotation[] annotations) {
        if (annotations != null && annotations.length > 0) {
            for (Annotation annotation : annotations) {
                if (BeanValidateAnnotationType.isValidateAnnotation(annotation.annotationType().getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private Method getMethodParameters(ProceedingJoinPoint proceedingJoinPoint) {
        Signature signature = proceedingJoinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Method targetMethod = methodSignature.getMethod();
            return targetMethod;
        }
        return null;
    }

    /**
     * 释放资源
     */
    private void releaseSource() {
        RequestParamContext.remove();
    }

}