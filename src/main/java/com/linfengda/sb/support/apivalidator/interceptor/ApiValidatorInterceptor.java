package com.linfengda.sb.support.apivalidator.interceptor;

import com.linfengda.sb.support.apivalidator.ApiParameterValidator;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 描述: api方法拦截器
 *
 * @author linfengda
 * @create 2020-03-24 15:16
 */
@Slf4j
public class ApiValidatorInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.debug("拦截controller方法{}进行入参校验，参数：{}", invocation.getMethod().getName(), invocation.getArguments());
        ApiParameterValidator apiParameterValidator = new ApiParameterValidator();
        apiParameterValidator.validateControllerMethodParameter(invocation);
        return invocation.proceed();
    }
}