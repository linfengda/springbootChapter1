package com.linfengda.sb.support.redis.cache.builder;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.chapter1.common.exception.entity.ErrorCode;
import com.linfengda.sb.support.redis.Constant;
import com.linfengda.sb.support.redis.cache.annotation.CacheKey;
import com.linfengda.sb.support.redis.cache.annotation.DeleteCache;
import com.linfengda.sb.support.redis.cache.annotation.QueryCache;
import com.linfengda.sb.support.redis.cache.annotation.UpdateCache;
import com.linfengda.sb.support.redis.cache.entity.meta.CacheKeyMeta;
import com.linfengda.sb.support.redis.cache.entity.meta.CacheMethodMeta;
import com.linfengda.sb.support.redis.cache.entity.type.CacheAnnotationType;
import com.linfengda.sb.support.redis.cache.entity.type.CacheMaxSizeStrategy;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
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
        if (null != cacheMethodMeta) {
            return cacheMethodMeta;
        }
        cacheMethodMeta = loadCacheAnnotation(method);
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
            cacheMethodMeta.setCacheAnnotationType(type);
            if (CacheAnnotationType.QUERY == type) {
                QueryCache queryCache = (QueryCache) cacheAnnotation;
                cacheMethodMeta.setDataType(queryCache.type());
                cacheMethodMeta.setPrefix(queryCache.prefix());
                cacheMethodMeta.setTimeOut(queryCache.timeOut());
                cacheMethodMeta.setTimeUnit(queryCache.timeUnit());
                cacheMethodMeta.setPreCacheSnowSlide(queryCache.preCacheSnowSlide());
                cacheMethodMeta.setPreCacheSnowSlideTime(queryCache.preCacheSnowSlideTime());
                cacheMethodMeta.setPreCacheHotKeyMultiLoad(queryCache.preCacheHotKeyMultiLoad());
                cacheMethodMeta.setMaxSize(queryCache.maxSize());
                cacheMethodMeta.setMaxSizeStrategy(queryCache.maxSizeStrategy());
                cacheMethodMeta.setDeleteLruBatchNum(queryCache.deleteLruBatchNum());
                cacheMethodMeta.setSpinTime(queryCache.spinTime());
                cacheMethodMeta.setMaxSpinCount(queryCache.maxSpinCount());
                break;
            }else if (CacheAnnotationType.UPDATE == type) {
                UpdateCache updateCache = (UpdateCache) cacheAnnotation;
                cacheMethodMeta.setDataType(updateCache.type());
                cacheMethodMeta.setPrefix(updateCache.prefix());
                cacheMethodMeta.setTimeOut(updateCache.timeOut());
                cacheMethodMeta.setTimeUnit(updateCache.timeUnit());
                cacheMethodMeta.setPreCacheSnowSlide(updateCache.preCacheSnowSlide());
                cacheMethodMeta.setPreCacheSnowSlideTime(updateCache.preCacheSnowSlideTime());
                break;
            }else if (CacheAnnotationType.DELETE == type) {
                DeleteCache deleteCache = (DeleteCache) cacheAnnotation;
                cacheMethodMeta.setDataType(deleteCache.type());
                cacheMethodMeta.setPrefix(StringUtils.isBlank(deleteCache.prefix()) ? method.getName() : deleteCache.prefix());
                cacheMethodMeta.setAllEntries(deleteCache.allEntries());
                break;
            }
        }
        validateCacheMethod(cacheMethodMeta);
        return cacheMethodMeta;
    }

    /**
     * 检查缓存方法，将使用错误遏止在开发阶段
     * @param cacheMethodMeta
     */
    private static void validateCacheMethod(CacheMethodMeta cacheMethodMeta) {
        if (CacheAnnotationType.DELETE == cacheMethodMeta.getCacheAnnotationType()) {
            return;
        }
        if (CacheAnnotationType.UPDATE == cacheMethodMeta.getCacheAnnotationType()) {
            long timeOut = cacheMethodMeta.getTimeOut();
            if (Constant.DEFAULT_NO_EXPIRE_TIME > timeOut) {
                throw new BusinessException("非法的缓存过期时间："+ timeOut +"！");
            }
            if (cacheMethodMeta.getPreCacheSnowSlide() && Constant.DEFAULT_NO_EXPIRE_TIME == timeOut) {
                throw new BusinessException("未设置缓存过期时间，无需防止缓存雪崩！");
            }
        }else if (CacheAnnotationType.QUERY == cacheMethodMeta.getCacheAnnotationType()) {
            long timeOut = cacheMethodMeta.getTimeOut();
            if (Constant.DEFAULT_NO_EXPIRE_TIME > timeOut) {
                throw new BusinessException("非法的缓存过期时间："+ timeOut +"！");
            }
            if (cacheMethodMeta.getPreCacheSnowSlide() && Constant.DEFAULT_NO_EXPIRE_TIME == timeOut) {
                throw new BusinessException("未设置缓存过期时间，无需防止缓存雪崩！");
            }
            if (Constant.DEFAULT_NO_SIZE_LIMIT > cacheMethodMeta.getMaxSize()) {
                throw new BusinessException("非法的缓存最大数量："+ cacheMethodMeta.getMaxSize() +"！");
            }
            if (CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_LRU == cacheMethodMeta.getMaxSizeStrategy() && Constant.DEFAULT_NO_EXPIRE_TIME == timeOut) {
                throw new BusinessException("未设置缓存过期时间，无法启用LRU缓存淘汰策略！");
            }
        }
        String returnType = cacheMethodMeta.getMethod().getReturnType().getName();
        boolean illegalReturnType = (DataType.LIST == cacheMethodMeta.getDataType() || DataType.SET == cacheMethodMeta.getDataType()) && !cacheMethodMeta.getDataType().getJavaType().equals(returnType);
        if (illegalReturnType) {
            throw new BusinessException("缓存数据类型与方法返回类型是否一致！，缓存数据类型：" + cacheMethodMeta.getDataType() + "，方法返回类型：" + returnType);
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
