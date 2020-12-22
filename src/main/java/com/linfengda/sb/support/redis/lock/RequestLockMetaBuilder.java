package com.linfengda.sb.support.redis.lock;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.support.redis.lock.annotation.RequestLock;
import com.linfengda.sb.support.redis.lock.annotation.RequestLockKey;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: linfengda
 * @date: 2020-12-22 14:47
 */
public class RequestLockMetaBuilder {
    /**
     * 支持的requestLockKey字段类型
     */
    private static final List<String> REQUEST_LOCK_KEY_SUPPORT_TYPE = Arrays.asList(String.class.getName(), Integer.class.getName());

    /**
     * 获取方法的业务锁注解
     */
    private static RequestLock getRequestLock(Method method){
        RequestLock requestLock = method.getDeclaredAnnotation(RequestLock.class);
        return requestLock;
    }

    /**
     * 获取方法的业务锁key列表
     * @param method    方法
     * @return          key列表
     */
    private static List<RequestLockKeyMeta> getRequestLockKey(Method method) {
        List<RequestLockKeyMeta> lockKeyMetas = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        if (null == parameters || 0 == parameters.length) {
            return lockKeyMetas;
        }
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            RequestLockKey requestLockKey = parameter.getAnnotation(RequestLockKey.class);
            if (null != requestLockKey) {
                String parameterType = parameter.getType().getName();
                if (!REQUEST_LOCK_KEY_SUPPORT_TYPE.contains(parameterType)) {
                    throw new BusinessException("不支持的RequestLockKey类型！");
                }
                continue;
            }
            RequestLockKeyMeta lockKeyMeta = new RequestLockKeyMeta();
            lockKeyMeta.setParameter(parameter);
            lockKeyMeta.setIndex(i);
            lockKeyMetas.add(lockKeyMeta);
        }
        return lockKeyMetas;
    }
}
