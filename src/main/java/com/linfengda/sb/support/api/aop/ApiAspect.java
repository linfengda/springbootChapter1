package com.linfengda.sb.support.api.aop;

import com.linfengda.sb.support.api.context.RequestParamContext;
import com.linfengda.sb.support.api.entity.RequestParam;
import com.linfengda.sb.support.api.util.HttpServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;

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

        RequestParam requestParam = null;
        Object result = null;
        try {
            requestParam = HttpServletUtil.getParams();
            RequestParamContext.setParam(requestParam);

            Object[] args = proceedingJoinPoint.getArgs();
            result = proceedingJoinPoint.proceed(args);
            return result;
        } finally {
            releaseSource();
        }
    }

    /**
     * 释放资源
     */
    private void releaseSource() {
        RequestParamContext.remove();
    }

}