package com.linfengda.sb.chapter1.common.api.aop;

import com.linfengda.sb.chapter1.common.api.router.RequestRouter;
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
    public void apiPoint() {

    }

    @Around("apiPoint()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return RequestRouter.INSTANCE.doRouter(proceedingJoinPoint);
    }
}