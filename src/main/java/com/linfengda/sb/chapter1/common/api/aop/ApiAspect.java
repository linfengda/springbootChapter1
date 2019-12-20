package com.linfengda.sb.chapter1.common.api.aop;

import com.linfengda.sb.chapter1.common.api.context.RequestParamContext;
import com.linfengda.sb.chapter1.common.api.entity.BaseType;
import com.linfengda.sb.chapter1.common.api.parameter.MesValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
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
        // TODO 校验基本数据类型
        Parameter[] parameters = getMethodParameters(proceedingJoinPoint);
        Object [] args = proceedingJoinPoint.getArgs();
        if (args != null && args.length > 0){
            for (int i=0; i <args.length; i++){
                Parameter parameter = parameters[i];
                if (BaseType.isBaseDataType(parameter.getType().getName())) {

                    MesValidateUtils.validate(args[i]);


                }else {
                    Annotation[] annotations = parameter.getAnnotations();
                    if (annotations != null && annotations.length > 0) {
                        // 判断是否有需要校验的自定义注解
                        for (Annotation annotation : annotations) {
                            if (annotation instanceof Valid) {
                                MesValidateUtils.validate(args[i]);
                            }
                        }
                    }
                }
            }
        }
        return args;
    }

    private Parameter[] getMethodParameters(ProceedingJoinPoint proceedingJoinPoint) {
        Signature signature = proceedingJoinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Method targetMethod = methodSignature.getMethod();
            Parameter[] parameters = targetMethod.getParameters();
            return parameters;
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