package com.linfengda.sb.chapter1.common.api.parameter;

import com.linfengda.sb.chapter1.common.api.parameter.type.BaseParameterType;
import com.linfengda.sb.chapter1.common.api.parameter.type.BeanValidateAnnotationType;
import com.linfengda.sb.chapter1.common.api.parameter.type.FieldValidateAnnotationType;
import com.linfengda.sb.chapter1.common.api.parameter.type.NotValidateParameterType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 描述: API入参校验
 *
 * @author linfengda
 * @create 2020-03-24 17:44
 */
public class ApiParameterValidator {

    /**
     * 入参校验
     */
    public void validateControllerMethodParameter(ProceedingJoinPoint proceedingJoinPoint) {
        Object [] args = proceedingJoinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }

        boolean needValidateBaseType = false;
        Method targetMethod = getMethodParameters(proceedingJoinPoint);
        Parameter[] parameters = targetMethod.getParameters();
        for (int i=0; i <parameters.length; i++){
            Parameter parameter = parameters[i];
            Annotation[] annotations = parameter.getAnnotations();
            if (NotValidateParameterType.isNotValidateParameterType(parameter.getType().getName())) {
                continue;
            }
            if (BaseParameterType.isBaseType(parameter.getType().getName())) {
                // 校验基本数据类型
                if (hasFieldValidateAnnotationType(annotations)) {
                    needValidateBaseType = true;
                    continue;
                }
            }else {
                // 校验Bean
                if (hasBeanValidateAnnotationType(annotations)) {
                    MyValidateUtils.validate(args[i]);
                }
            }
        }
        if (needValidateBaseType) {
            MyValidateUtils.validateParameters(proceedingJoinPoint.getTarget(), targetMethod, args);
        }
    }

    /**
     * 判断是否有基本数据类型的校验注解
     * @param annotations   参数的注解列表
     * @return              true：有校验注解，false：无校验注解
     */
    private boolean hasFieldValidateAnnotationType(Annotation[] annotations) {
        if (annotations != null && annotations.length > 0) {
            for (Annotation annotation : annotations) {
                if (FieldValidateAnnotationType.isValidateAnnotation(annotation.annotationType().getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否有Bean类型的校验注解
     * @param annotations   参数的注解列表
     * @return              true：有校验注解，false：无校验注解
     */
    private boolean hasBeanValidateAnnotationType(Annotation[] annotations) {
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
}
