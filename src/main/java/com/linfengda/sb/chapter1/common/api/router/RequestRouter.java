package com.linfengda.sb.chapter1.common.api.router;

import com.linfengda.sb.chapter1.common.api.context.RequestContext;
import com.linfengda.sb.chapter1.common.api.entity.RequestInfoBO;
import com.linfengda.sb.chapter1.common.api.parameter.MyValidateUtils;
import com.linfengda.sb.chapter1.common.api.parameter.validate.BaseValidateParameterType;
import com.linfengda.sb.chapter1.common.api.parameter.validate.BeanValidateAnnotationType;
import com.linfengda.sb.chapter1.common.api.parameter.validate.FieldValidateAnnotationType;
import com.linfengda.sb.chapter1.common.api.parameter.validate.NotValidateParameterType;
import com.linfengda.sb.chapter1.common.api.router.impl.PCRequestHandler;
import com.linfengda.sb.chapter1.common.api.router.impl.WeChatRequestHandler;
import com.linfengda.sb.chapter1.common.api.util.HttpServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 描述: 接口路由
 *
 * @author linfengda
 * @create 2019-12-19 17:52
 */
@Slf4j
public enum RequestRouter {
    INSTANCE;

    public Object doRouter(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            // 1.获取请求信息上下文
            RequestInfoBO requestInfoBO = HttpServletUtil.getRequestInfoBO();
            RequestContext.setParam(requestInfoBO);

            // 2.根据url进行路由
            AbstractRequestHandler requestHandler = getRequestHandler(requestInfoBO);
            requestHandler.doHandler();

            // 3.在路由之后进行入参校验
            Object[] args = checkControllerMethodParameter(proceedingJoinPoint);
            Object result = proceedingJoinPoint.proceed(args);
            return result;
        } finally {
            releaseSource();
        }
    }

    /**
     * 根据url进行路由
     * @param requestInfoBO   请求信息BO
     * @return
     * @throws Exception
     */
    public static AbstractRequestHandler getRequestHandler(RequestInfoBO requestInfoBO) throws Exception {
        BizModuleType bizModuleType = BizModuleType.getModuleType(requestInfoBO.getUrl());
        switch (bizModuleType) {
            case PC: return new PCRequestHandler(requestInfoBO);
            case WeChat: return new WeChatRequestHandler(requestInfoBO);
        }
        return null;
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
        RequestContext.remove();
    }
}
