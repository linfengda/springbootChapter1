package com.linfengda.sb.chapter1.common.cache.aop;

import com.linfengda.sb.chapter1.common.cache.FastCache;
import com.linfengda.sb.chapter1.common.cache.annotation.Cache;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private FastCache fastCache;

    @Around("@annotation(cache)")
    public Object around(ProceedingJoinPoint point, Cache cache) throws Throwable {
        // 获取注解属性
        String key = cache.prefix();
        String[] keys = cache.keys();
        long timeOut = cache.timeOut();
        TimeUnit timeUnit = cache.timeUnit();
        // 拼接缓存key
        Object[] args = point.getArgs();
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Class<T> resultType = methodSignature.getReturnType();
        String[] parameterNames = methodSignature.getParameterNames();
        if (null != keys && keys.length > 0) {
            if (null != parameterNames && parameterNames.length > 0) {
                for (int i = 0; i < parameterNames.length; i++) {
                    String parameterName = parameterNames[i];
                    if (hitKeyName(parameterName, keys)) {
                        if (null == args[i]) {
                            continue;
                        }
                        key += ":" + args[i].toString();
                    }
                }
            }
        }
        // 从缓存获取结果
        Object result = fastCache.getCache(key, resultType);
        if (null == result) {
            // 从方法获取结果
            result = point.proceed();
            fastCache.cache(key, result, timeOut, timeUnit);
        }
        return result;
    }

    private boolean hitKeyName(String parameterName, String[] keys) {
        for (String key : keys) {
            if (key.equals(parameterName)) {
                return true;
            }
        }
        return false;
    }
}
