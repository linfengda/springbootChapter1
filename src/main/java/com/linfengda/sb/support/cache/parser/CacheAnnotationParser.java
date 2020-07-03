package com.linfengda.sb.support.cache.parser;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.chapter1.common.exception.entity.ErrorCode;
import com.linfengda.sb.support.cache.annotation.CacheKey;
import com.linfengda.sb.support.cache.annotation.DeleteCache;
import com.linfengda.sb.support.cache.annotation.QueryCache;
import com.linfengda.sb.support.cache.annotation.UpdateCache;
import com.linfengda.sb.support.cache.entity.meta.CacheKeyMeta;
import com.linfengda.sb.support.cache.entity.meta.CacheMethodMeta;
import com.linfengda.sb.support.cache.entity.type.OperationType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 描述: 缓存注解解析
 *
 * @author linfengda
 * @create 2020-03-24 15:14
 */
public class CacheAnnotationParser {
    /**
     * 方法是否有缓存注解
     */
    private static final Map<Method, Boolean> CACHE_METHOD_FLAG_CACHE = new ConcurrentHashMap<>(1024);
    /**
     * 缓存注解方法
     */
    private static final Map<Method, CacheMethodMeta> CACHE_METHOD_CACHE = new ConcurrentHashMap<>(512);


    /**
     * 判断方法是否有缓存注解（当方法有多个缓存注解时报错）
     * @param method    方法
     * @return          true：有缓存注解，false：无缓存注解
     */
    protected boolean hasCacheAnnotation(Method method) {
        Boolean flag = CACHE_METHOD_FLAG_CACHE.get(method);
        if (null != flag) {
            return flag;
        }
        return checkCacheAnnotation(method);
    }

    /**
     * 判断方法是否有缓存注解（当方法有多个缓存注解时报错）
     * @param method    方法
     * @return          true：有缓存注解，false：无缓存注解
     */
    private static boolean checkCacheAnnotation(Method method) {
        List<Annotation> cacheAnnotationList = getMethodCacheAnnotations(method);
        if (CollectionUtils.isEmpty(cacheAnnotationList)) {
            CACHE_METHOD_FLAG_CACHE.put(method, false);
            return false;
        }
        if (cacheAnnotationList.size() > 1) {
            throw new BusinessException(ErrorCode.COMMON_CACHE_ERROR_CODE, "方法["+ method.getName() +"]不能添加多个缓存注解！");
        }
        CACHE_METHOD_FLAG_CACHE.put(method, true);
        return true;
    }

    /**
     * 获取方法缓存注解
     * @param method    方法
     * @return          缓存注解列表
     */
    private static List<Annotation> getMethodCacheAnnotations(Method method) {
        Annotation[] annotations = method.getAnnotations();
        if (null == annotations || annotations.length == 0) {
            return null;
        }
        List<Annotation> annotationList = Arrays.asList(annotations);
        List<Annotation> cacheAnnotationList = annotationList.stream().filter(an -> OperationType.isCacheAnnotation(an.getClass())).collect(Collectors.toList());
        return cacheAnnotationList;
    }


    /**
     * 获取缓存注解信息
     * @param method    方法参数
     * @return          缓存方法信息
     */
    protected static CacheMethodMeta getCacheAnnotation(Method method) {
        CacheMethodMeta cacheMethodMeta = CACHE_METHOD_CACHE.get(method);
        if (null == cacheMethodMeta) {
            cacheMethodMeta = loadCacheAnnotation(method);
        }
        return cacheMethodMeta;
    }

    /**
     * 加载缓存注解信息
     * @param method    方法参数
     * @return          缓存方法信息
     */
    private static CacheMethodMeta loadCacheAnnotation(Method method) {
        CacheMethodMeta cacheMethodMeta = parseCacheAnnotation(method);
        CACHE_METHOD_CACHE.put(method, cacheMethodMeta);
        return cacheMethodMeta;
    }

    /**
     * 解析缓存方法注解信息
     * @param method    方法参数
     * @return          缓存方法信息
     */
    private static CacheMethodMeta parseCacheAnnotation(Method method) {
        CacheMethodMeta cacheMethodMeta = new CacheMethodMeta();
        cacheMethodMeta.setMethod(method);
        cacheMethodMeta.setMethodName(method.getName());
        cacheMethodMeta.setReturnType(method.getReturnType());

        for (OperationType type : OperationType.values()) {
            Annotation cacheAnnotation = method.getAnnotation(type.getAnnotation());
            if (null == cacheAnnotation) {
                continue;
            }
            if (OperationType.QUERY == type) {
                QueryCache queryCache = (QueryCache) cacheAnnotation;
                cacheMethodMeta.setAnnotationType(OperationType.QUERY);
                cacheMethodMeta.setPrefix(StringUtils.isBlank(queryCache.prefix()) ? method.getName() : queryCache.prefix());
                cacheMethodMeta.setTimeOut(queryCache.timeOut());
                cacheMethodMeta.setTimeUnit(queryCache.timeUnit());
                cacheMethodMeta.setKeys(getCacheKeys(method));
                return cacheMethodMeta;
            }else if (OperationType.UPDATE == type) {
                UpdateCache updateCache = (UpdateCache) cacheAnnotation;
                cacheMethodMeta.setAnnotationType(OperationType.UPDATE);
                cacheMethodMeta.setPrefix(StringUtils.isBlank(updateCache.prefix()) ? method.getName() : updateCache.prefix());
                cacheMethodMeta.setTimeOut(updateCache.timeOut());
                cacheMethodMeta.setTimeUnit(updateCache.timeUnit());
                cacheMethodMeta.setKeys(getCacheKeys(method));
                return cacheMethodMeta;
            }else if (OperationType.UPDATE == type) {
                DeleteCache deleteCache = (DeleteCache) cacheAnnotation;
                cacheMethodMeta.setAnnotationType(OperationType.DELETE);
                cacheMethodMeta.setPrefix(StringUtils.isBlank(deleteCache.prefix()) ? method.getName() : deleteCache.prefix());
                cacheMethodMeta.setKeys(getCacheKeys(method));
                cacheMethodMeta.setAllEntries(deleteCache.allEntries());
                return cacheMethodMeta;
            }
        }
        return null;
    }

    /**
     * 获取缓存方法key列表
     * @param method    方法
     * @return          缓存key列表
     */
    private static List<CacheKeyMeta> getCacheKeys(Method method) {
        List<CacheKeyMeta> cacheKeyMetas = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        if (null == parameters || 0 == parameters.length) {
            return cacheKeyMetas;
        }
        for (Parameter parameter : parameters) {
            CacheKey cacheKey = parameter.getAnnotation(CacheKey.class);
            if (null == cacheKey) {
                continue;
            }
            CacheKeyMeta cacheKeyMeta = new CacheKeyMeta();
            cacheKeyMeta.setParameter(parameter);
            cacheKeyMeta.setParameterName(parameter.getName());
            cacheKeyMeta.setNullable(cacheKey.nullable());
            cacheKeyMeta.setNullKey(cacheKey.nullKey());
            cacheKeyMetas.add(cacheKeyMeta);
        }
        return cacheKeyMetas;
    }
}
