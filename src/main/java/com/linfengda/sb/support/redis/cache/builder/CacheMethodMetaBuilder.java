package com.linfengda.sb.support.redis.cache.builder;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.chapter1.common.exception.entity.ErrorCode;
import com.linfengda.sb.support.redis.Constant;
import com.linfengda.sb.support.redis.cache.annotation.CacheKey;
import com.linfengda.sb.support.redis.cache.annotation.DeleteCache;
import com.linfengda.sb.support.redis.cache.annotation.QueryCache;
import com.linfengda.sb.support.redis.cache.annotation.UpdateCache;
import com.linfengda.sb.support.redis.cache.entity.meta.*;
import com.linfengda.sb.support.redis.cache.entity.type.CacheAnnotationType;
import com.linfengda.sb.support.redis.cache.entity.type.CacheMaxSizeStrategy;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述: 缓存方法注解解析并缓存
 *
 * @author linfengda
 * @create 2020-03-24 15:14
 */
public class CacheMethodMetaBuilder {
    /**
     * 缓存方法标识
     */
    private static final Map<Method, Boolean> CHECKED_CACHE_METHOD_CACHE = new ConcurrentHashMap<>(512);
    /**
     * 缓存方法注解元数据
     */
    private static final Map<Method, CacheMethodMeta> CACHE_METHOD_CACHE = new ConcurrentHashMap<>(512);


    /**
     * 判断方法是否有缓存注解（当方法有多个缓存注解时报错）
     * @param method    方法
     * @return          true：有缓存注解，false：无缓存注解
     */
    public static boolean checkCacheAnnotation(Method method) {
        Boolean flag = CHECKED_CACHE_METHOD_CACHE.get(method);
        if (null != flag) {
            return flag;
        }
        List<Annotation> cacheAnnotationList = getMethodCacheAnnotations(method);
        if (CollectionUtils.isEmpty(cacheAnnotationList)) {
            throw new BusinessException(ErrorCode.COMMON_CACHE_ERROR_CODE, "方法["+ method.getName() +"]没有缓存注解！");
        }
        if (cacheAnnotationList.size() > 1) {
            throw new BusinessException(ErrorCode.COMMON_CACHE_ERROR_CODE, "方法["+ method.getName() +"]不能添加多个缓存注解！");
        }
        CHECKED_CACHE_METHOD_CACHE.put(method, true);
        return true;
    }

    /**
     * 获取方法缓存注解
     * @param method    方法
     * @return          缓存注解列表
     */
    private static List<Annotation> getMethodCacheAnnotations(Method method) {
        List<Annotation> cacheAnnotationList = new ArrayList<>();
        for (CacheAnnotationType annotationType : CacheAnnotationType.values()) {
            Annotation annotation = method.getDeclaredAnnotation(annotationType.getAnnotation());
            if (null == annotation) {
                continue;
            }
            cacheAnnotationList.add(annotation);
        }
        return cacheAnnotationList;
    }

    /**
     *获取缓存信息
     * @param method    方法
     * @return          缓存方法元数据
     */
    public static CacheMethodMeta getCacheMethodMeta(Method method) {
        checkCacheAnnotation(method);
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
        cacheMethodMeta.setMethodCacheKeys(getKeyMetas(method));
        for (CacheAnnotationType type : CacheAnnotationType.values()) {
            Annotation cacheAnnotation = method.getAnnotation(type.getAnnotation());
            if (null == cacheAnnotation) {
                continue;
            }
            if (CacheAnnotationType.QUERY == type) {
                QueryCache queryCache = (QueryCache) cacheAnnotation;
                cacheMethodMeta.setDataType(queryCache.type());
                cacheMethodMeta.setPrefix(queryCache.prefix());
                CacheQueryMeta cacheQueryMeta = new CacheQueryMeta();
                cacheQueryMeta.setTimeOut(queryCache.timeOut());
                cacheQueryMeta.setTimeUnit(queryCache.timeUnit());
                cacheQueryMeta.setPreCacheSnowSlide(queryCache.preCacheSnowSlide());
                cacheQueryMeta.setPreCacheHotKeyMultiLoad(queryCache.preCacheHotKeyMultiLoad());
                cacheQueryMeta.setMaxSizeStrategy(queryCache.maxSizeStrategy());
                cacheQueryMeta.setMaxSize(queryCache.maxSize());
                cacheQueryMeta.setSpinTime(queryCache.spinTime());
                cacheQueryMeta.setMaxSpinCount(queryCache.maxSpinCount());
                cacheMethodMeta.setQueryMeta(cacheQueryMeta);
                return cacheMethodMeta;
            }else if (CacheAnnotationType.UPDATE == type) {
                UpdateCache updateCache = (UpdateCache) cacheAnnotation;
                cacheMethodMeta.setDataType(updateCache.type());
                cacheMethodMeta.setPrefix(updateCache.prefix());
                CacheUpdateMeta cacheUpdateMeta = new CacheUpdateMeta();
                cacheUpdateMeta.setTimeOut(updateCache.timeOut());
                cacheUpdateMeta.setTimeUnit(updateCache.timeUnit());
                cacheUpdateMeta.setPreCacheSnowSlide(updateCache.preCacheSnowSlide());
                cacheMethodMeta.setUpdateMeta(cacheUpdateMeta);
                return cacheMethodMeta;
            }else if (CacheAnnotationType.UPDATE == type) {
                DeleteCache deleteCache = (DeleteCache) cacheAnnotation;
                cacheMethodMeta.setDataType(deleteCache.type());
                cacheMethodMeta.setPrefix(StringUtils.isBlank(deleteCache.prefix()) ? method.getName() : deleteCache.prefix());
                CacheDeleteMeta cacheDeleteMeta = new CacheDeleteMeta();
                cacheDeleteMeta.setAllEntries(deleteCache.allEntries());
                cacheMethodMeta.setDeleteMate(cacheDeleteMeta);
                return cacheMethodMeta;
            }
        }
        validateCacheMethod(cacheMethodMeta);
        return null;
    }

    /**
     * 检查缓存方法，将使用错误遏止在开发阶段
     * @param cacheMethodMeta
     */
    private static void validateCacheMethod(CacheMethodMeta cacheMethodMeta) {
        if (!Constant.DEFAULT_NO_SIZE_LIMIT.equals(cacheMethodMeta.getQueryMeta().getMaxSize()) && 0 >= cacheMethodMeta.getQueryMeta().getMaxSize()) {
            throw new BusinessException("非法的最大缓存数量："+ cacheMethodMeta.getQueryMeta().getMaxSize() +"！");
        }
        if (cacheMethodMeta.getQueryMeta().getMaxSizeStrategy() == CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_ABANDON || cacheMethodMeta.getQueryMeta().getMaxSizeStrategy() == CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_LRU) {
            if (Constant.DEFAULT_NO_SIZE_LIMIT.equals(cacheMethodMeta.getQueryMeta().getMaxSize())) {
                throw new BusinessException("未限制最大缓存数量，无法启用淘汰策略！");
            }
            if (cacheMethodMeta.getQueryMeta().getMaxSizeStrategy() == CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_LRU && Constant.DEFAULT_NO_EXPIRE_TIME.equals(cacheMethodMeta.getQueryMeta().getTimeOut())) {
                throw new BusinessException("未限制缓存时间，无法启用LRU算法淘汰数据！");
            }
        }
    }

    /**
     * 获取缓存方法key列表
     * @param method    方法
     * @return          缓存key列表
     */
    private static List<CacheKeyMeta> getKeyMetas(Method method) {
        List<CacheKeyMeta> keyMetas = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        if (null == parameters || 0 == parameters.length) {
            return keyMetas;
        }
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            CacheKey cacheKey = parameter.getAnnotation(CacheKey.class);
            if (null == cacheKey) {
                continue;
            }
            CacheKeyMeta keyMeta = new CacheKeyMeta();
            keyMeta.setParameter(parameter);
            keyMeta.setIndex(i);
            keyMeta.setNullKey(cacheKey.nullKey());
            keyMetas.add(keyMeta);
        }
        return keyMetas;
    }
}
