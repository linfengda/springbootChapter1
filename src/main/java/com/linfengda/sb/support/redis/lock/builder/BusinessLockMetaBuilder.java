package com.linfengda.sb.support.redis.lock.builder;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.support.redis.lock.annotation.BusinessLock;
import com.linfengda.sb.support.redis.lock.annotation.BusinessLockKey;
import com.linfengda.sb.support.redis.lock.meta.LockKeyMeta;
import com.linfengda.sb.support.redis.lock.meta.LockMethodMeta;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: linfengda
 * @date: 2020-12-22 14:47
 */
public class BusinessLockMetaBuilder {
    /**
     * 支持的requestLockKey字段类型
     */
    private static final List<String> REQUEST_LOCK_KEY_SUPPORT_TYPE = Arrays.asList(String.class.getName(), Integer.class.getName());
    /**
     * 业务锁方法注解元数据
     */
    private static final Map<Method, LockMethodMeta> LOCK_METHOD_CACHE = new ConcurrentHashMap<>(512);



    /**
     * 获取注解信息
     * @param method    方法
     * @return          注解信息
     */
    public static LockMethodMeta getLockMethodMeta(Method method) {
        if (LOCK_METHOD_CACHE.containsKey(method)) {
            return LOCK_METHOD_CACHE.get(method);
        }
        return loadLockAnnotation(method);
    }

    /**
     * 获取注解信息
     * @param method    方法参数
     * @return          注解信息
     */
    private static LockMethodMeta loadLockAnnotation(Method method) {
        BusinessLock businessLock = method.getDeclaredAnnotation(BusinessLock.class);
        if (null == businessLock) {
            throw new BusinessException("找不到的BusinessLock注解！");
        }
        List<LockKeyMeta> lockKeyMetas = parseLockKeyAnnotation(method);
        if (CollectionUtils.isEmpty(lockKeyMetas)) {
            throw new BusinessException("找不到的BusinessLockKey注解！");
        }
        LockMethodMeta lockMethodMeta = new LockMethodMeta();
        lockMethodMeta.setLockKeys(lockKeyMetas);
        LOCK_METHOD_CACHE.put(method, lockMethodMeta);
        return lockMethodMeta;
    }

    /**
     * 获取方法的业务锁key列表
     * @param method    方法
     * @return          key列表
     */
    private static List<LockKeyMeta> parseLockKeyAnnotation(Method method) {
        Parameter[] parameters = method.getParameters();
        if (null == parameters || 0 == parameters.length) {
            return null;
        }
        List<LockKeyMeta> lockKeyMetas = new ArrayList<>();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            LockKeyMeta lockKeyMeta = initKeyMeta(parameter, i);
            if (null == lockKeyMeta) {
                continue;
            }
            lockKeyMetas.add(lockKeyMeta);
        }
        lockKeyMetas.stream().sorted(Comparator.comparing(LockKeyMeta::getKeyIndex)).collect(Collectors.toList());
        return lockKeyMetas;
    }

    private static LockKeyMeta initKeyMeta(Parameter parameter, int i) {
        BusinessLockKey businessLockKey = parameter.getAnnotation(BusinessLockKey.class);
        if (null != businessLockKey) {
            if (!REQUEST_LOCK_KEY_SUPPORT_TYPE.contains(parameter.getType().getName())) {
                throw new BusinessException("不支持的RequestLockKey类型！");
            }
            LockKeyMeta lockKeyMeta = new LockKeyMeta();
            lockKeyMeta.setKeyParameter(parameter);
            lockKeyMeta.setKeyParameterIndex(i);
            lockKeyMeta.setKeyIndex(businessLockKey.index());
            return lockKeyMeta;
        }
        return initFiledKeyMeta(parameter, i);
    }

    private static LockKeyMeta initFiledKeyMeta(Parameter parameter, int i) {
        Field[] fields = parameter.getType().getDeclaredFields();
        if (0 == fields.length) {
            return null;
        }
        for (Field field : fields) {
            BusinessLockKey businessLockKey = field.getAnnotation(BusinessLockKey.class);
            if (null == businessLockKey) {
                continue;
            }
            if (!REQUEST_LOCK_KEY_SUPPORT_TYPE.contains(field.getType().getName())) {
                throw new BusinessException("不支持的RequestLockKey类型！");
            }
            LockKeyMeta lockKeyMeta = new LockKeyMeta();
            lockKeyMeta.setKeyParameter(parameter);
            lockKeyMeta.setKeyParameterIndex(i);
            lockKeyMeta.setKeyField(field);
            lockKeyMeta.setKeyIndex(businessLockKey.index());
            return lockKeyMeta;
        }
        return null;
    }
}
