package com.linfengda.sb.support.cache.aop;

import com.linfengda.sb.support.cache.annotation.Cache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 描述: 简单快速实现方法缓存AOP
 *
 * @author linfengda
 * @create 2019-08-14 19:32
 */
@Slf4j
@Aspect
@Component
public class FastCacheAspect {

    @Around("@annotation(cache)")
    public Object around(ProceedingJoinPoint point, Cache cache) throws Throwable {
        return point.proceed();
    }

    private boolean hitKeyName(String parameterName, String[] keys) throws Exception {
        for (String key : keys) {
            if (key.equals(parameterName)) {
                return true;
            }
        }
        return false;
    }
}
