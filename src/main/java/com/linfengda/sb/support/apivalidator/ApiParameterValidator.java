package com.linfengda.sb.support.apivalidator;

import com.linfengda.sb.support.exception.BusinessException;
import com.linfengda.sb.support.apivalidator.type.BaseType;
import com.linfengda.sb.support.apivalidator.type.BeanValidateAnnotationType;
import com.linfengda.sb.support.apivalidator.type.FieldValidateAnnotationType;
import com.linfengda.sb.support.apivalidator.type.NotValidateParameterType;
import com.linfengda.sb.support.validator.MyValidateUtils;
import org.aopalliance.intercept.MethodInvocation;

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
     * api参数个数限制
     */
    private static final int MAX_API_PARAMS_LIMIT = 3;


    public void validateControllerMethodParameter(MethodInvocation invocation) {
        Object [] args = invocation.getArguments();
        if (args == null || args.length == 0) {
            return;
        }
        if (MAX_API_PARAMS_LIMIT < args.length) {
            throw new BusinessException("api参数限制为" + MAX_API_PARAMS_LIMIT + "个！");
        }
        boolean needValidateBaseType = false;
        Method targetMethod = invocation.getMethod();
        Parameter[] parameters = targetMethod.getParameters();
        for (int i=0; i <parameters.length; i++){
            Parameter parameter = parameters[i];
            Annotation[] annotations = parameter.getAnnotations();
            if (NotValidateParameterType.isNotValidateParameterType(parameter.getType().getName())) {
                continue;
            }
            if (BaseType.isBaseType(parameter.getType().getName())) {
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
            MyValidateUtils.validateParameters(invocation.getThis(), targetMethod, args);
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
}
