package com.linfengda.sb.support.redis.cache.builder;

import com.linfengda.sb.support.exception.BusinessException;
import com.linfengda.sb.support.redis.Constant;
import com.linfengda.sb.support.redis.cache.annotation.*;
import com.linfengda.sb.support.redis.cache.entity.meta.CacheKeyMeta;
import com.linfengda.sb.support.redis.cache.entity.meta.CacheMethodMeta;
import com.linfengda.sb.support.redis.cache.entity.type.CacheAnnotationType;
import com.linfengda.sb.support.redis.cache.entity.type.CacheMaxSizeStrategy;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 描述: 缓存方法注解解析并缓存
 *
 * @author linfengda
 * @create 2020-07-24 15:14
 */
public class CacheMethodMetaBuilder {
    /**
     * 缓存方法注解元数据
     */
    private static final Map<Method, Map<CacheAnnotationType, CacheMethodMeta>> CACHE_METHOD_CACHE = new ConcurrentHashMap<>(512);


    /**
     * 获取缓存信息
     * @param method    方法
     * @return          缓存方法元数据
     */
    public static CacheMethodMeta getCacheMethodMeta(Method method, CacheAnnotationType annotationType) {
        if (CACHE_METHOD_CACHE.containsKey(method)) {
            return CACHE_METHOD_CACHE.get(method).get(annotationType);
        }
        Map<CacheAnnotationType, CacheMethodMeta> cacheMethodMetaMap = loadCacheAnnotation(method);
        return cacheMethodMetaMap.get(annotationType);
    }

    /**
     * 加载缓存注解信息
     * @param method    方法参数
     * @return          缓存方法信息
     */
    private static Map<CacheAnnotationType, CacheMethodMeta> loadCacheAnnotation(Method method) {
        List<CacheMethodMeta> cacheMethodMetas = parseCacheAnnotation(method);
        Map<CacheAnnotationType, CacheMethodMeta> cacheMethodMetaMap = cacheMethodMetas.stream().collect(Collectors.toMap(CacheMethodMeta::getCacheAnnotationType, v -> v, (k1, k2) -> k1));
        validateCacheMethod(cacheMethodMetaMap);
        CACHE_METHOD_CACHE.put(method, cacheMethodMetaMap);
        return cacheMethodMetaMap;
    }

    /**
     * 解析缓存方法注解信息
     * @param method    方法参数
     * @return          缓存方法信息
     */
    private static List<CacheMethodMeta> parseCacheAnnotation(Method method) {
        List<CacheMethodMeta> cacheMethodMetas = new ArrayList<>();
        for (CacheAnnotationType type : CacheAnnotationType.values()) {
            Annotation cacheAnnotation = method.getAnnotation(type.getAnnotation());
            if (null == cacheAnnotation) {
                continue;
            }
            CacheMethodMeta cacheMethodMeta = new CacheMethodMeta();
            cacheMethodMeta.setMethod(method);
            cacheMethodMeta.setMethodName(method.getName());
            cacheMethodMeta.setMethodCacheKeys(getKeyMetas(method));
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
            }else if (CacheAnnotationType.UPDATE == type) {
                UpdateCache updateCache = (UpdateCache) cacheAnnotation;
                cacheMethodMeta.setDataType(updateCache.type());
                cacheMethodMeta.setPrefix(updateCache.prefix());
                cacheMethodMeta.setTimeOut(updateCache.timeOut());
                cacheMethodMeta.setTimeUnit(updateCache.timeUnit());
                cacheMethodMeta.setPreCacheSnowSlide(updateCache.preCacheSnowSlide());
                cacheMethodMeta.setPreCacheSnowSlideTime(updateCache.preCacheSnowSlideTime());
            }else if (CacheAnnotationType.DELETE == type) {
                List<CacheMethodMeta> deleteMetas = new ArrayList<>();
                DeleteCache deleteCache = (DeleteCache) cacheAnnotation;
                Cache[] caches = deleteCache.caches();
                for (Cache cache : caches) {
                    CacheMethodMeta deleteMeta = new CacheMethodMeta();
                    deleteMeta.setMethod(cacheMethodMeta.getMethod());
                    deleteMeta.setMethodName(cacheMethodMeta.getMethodName());
                    deleteMeta.setMethodCacheKeys(cacheMethodMeta.getMethodCacheKeys());
                    deleteMeta.setCacheAnnotationType(cacheMethodMeta.getCacheAnnotationType());
                    deleteMeta.setDataType(cache.type());
                    deleteMeta.setPrefix(StringUtils.isEmpty(cache.prefix()) ? method.getName() : cache.prefix());
                    deleteMeta.setAllEntries(cache.allEntries());
                    deleteMetas.add(deleteMeta);
                }
                cacheMethodMeta.setDeleteMetas(deleteMetas);
            }
            validateCacheMethodMeta(cacheMethodMeta);
            cacheMethodMetas.add(cacheMethodMeta);
        }
        return cacheMethodMetas;
    }

    /**
     * 检查缓存方法，将使用错误遏止在开发阶段
     * @param cacheMethodMetaMap
     */
    private static void validateCacheMethod(Map<CacheAnnotationType, CacheMethodMeta> cacheMethodMetaMap) {
        if (null == cacheMethodMetaMap) {
            return;
        }
        if (cacheMethodMetaMap.containsKey(CacheAnnotationType.QUERY) && cacheMethodMetaMap.containsKey(CacheAnnotationType.DELETE)) {
            throw new BusinessException("方法请不要同时使用@QueryCache和@DeleteCache注解！");
        }
        if (cacheMethodMetaMap.containsKey(CacheAnnotationType.QUERY) && cacheMethodMetaMap.containsKey(CacheAnnotationType.UPDATE)) {
            throw new BusinessException("方法请不要同时使用@QueryCache和@UpdateCache注解！");
        }
    }

    /**
     * 检查缓存注解，将使用错误遏止在开发阶段
     * @param cacheMethodMeta
     */
    private static void validateCacheMethodMeta(CacheMethodMeta cacheMethodMeta) {
        if (CacheAnnotationType.DELETE == cacheMethodMeta.getCacheAnnotationType()) {
            if (CollectionUtils.isEmpty(cacheMethodMeta.getDeleteMetas())) {
                throw new BusinessException("@DeleteCache注解至少要定义一个删除的缓存！");
            }
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
