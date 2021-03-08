package com.linfengda.sb.support.apivalidator;

import com.linfengda.sb.support.apivalidator.type.BeanValidateAnnotationType;
import com.linfengda.sb.support.apivalidator.type.FieldValidateAnnotationType;
import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.support.validator.MyValidateUtils;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

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
        Object[] args = invocation.getArguments();
        Method targetMethod = invocation.getMethod();
        Parameter[] parameters = targetMethod.getParameters();
        if (args == null || args.length == 0 || parameters == null || parameters.length == 0) {
            return;
        }
        if (MAX_API_PARAMS_LIMIT < args.length) {
            throw new BusinessException("api参数限制为" + MAX_API_PARAMS_LIMIT + "个！");
        }
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            String parameterName = parameter.getType().getName();
            Annotation[] annotations = parameter.getAnnotations();
            if (Map.class.getName().equals(parameterName)) {
                continue;
            }
            if (List.class.getName().equals(parameterName)) {
                if (hasFieldValidateAnnotationType(annotations)) {
                    MyValidateUtils.validateParameters(invocation.getThis(), targetMethod, args);
                }
                if (hasBeanValidateAnnotationType(annotations)) {
                    List list = (List) args[i];
                    for (Object obj : list) {
                        MyValidateUtils.validate(obj);
                    }
                }
                continue;
            }
            if (hasFieldValidateAnnotationType(annotations)) {
                MyValidateUtils.validateParameters(invocation.getThis(), targetMethod, args);
            }
            if (hasBeanValidateAnnotationType(annotations)) {
                MyValidateUtils.validate(args[i]);
            }
        }
    }

    /**
     * 判断是否有基本数据类型的校验注解
     *
     * @param annotations 参数的注解列表
     * @return true：有校验注解，false：无校验注解
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
     *
     * @param annotations 参数的注解列表
     * @return true：有校验注解，false：无校验注解
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
