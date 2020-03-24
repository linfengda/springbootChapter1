package com.linfengda.sb.chapter1.common.api.parameter;

import com.linfengda.sb.chapter1.common.api.parameter.type.BaseValidateParameterType;
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
public class MyParameterValidator {

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
}
