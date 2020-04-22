package com.linfengda.sb.support.cache.parser;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.chapter1.common.exception.entity.ErrorCode;
import com.linfengda.sb.support.cache.annotation.*;
import com.linfengda.sb.support.cache.entity.CacheMethodMeta;
import com.linfengda.sb.support.cache.manager.CacheAnnotationManager;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
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
    private static final Map<Method, Boolean> CACHE_ANNOTATION_METHOD_FLAG_CACHE = new ConcurrentHashMap<>(512);
    /**
     * 缓存注解方法
     */
    private static final Map<Method, Boolean> CACHE_ANNOTATION_METHOD_CACHE = new ConcurrentHashMap<>(256);


    /**
     * 判断方法是否有缓存注解（当方法有多个缓存注解时报错）
     * @param method    方法
     * @return          true：有缓存注解，false：无缓存注解
     */
    public static boolean hasCacheAnnotation(Method method) {
        Boolean flag = CACHE_ANNOTATION_METHOD_FLAG_CACHE.get(method);
        if (null != flag) {
            return flag;
        }
        Annotation[] annotations = method.getAnnotations();
        if (null == annotations || annotations.length == 0) {
            return false;
        }
        List<Annotation> annotationList = Arrays.asList(annotations);
        List<Annotation> cacheAnnotationList = annotationList.stream().filter(an -> CacheAnnotationManager.ALL_CACHE_ANNOTATIONS.getAnnotations().contains(an)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(cacheAnnotationList)) {
            return false;
        }
        if (cacheAnnotationList.size() > 1) {
            throw new BusinessException(ErrorCode.COMMON_CACHE_ERROR_CODE, "方法["+ method.getName() +"]不能添加多个缓存注解！");
        }
        return true;
    }

    /**
     * 初始化缓存注解信息
     * @param method
     * @param cacheMethodMeta
     */
    public static void initAnnotationValue(Method method, CacheMethodMeta cacheMethodMeta) {
        for (Class<? extends Annotation> annotationType : CacheAnnotationManager.ALL_CACHE_ANNOTATIONS.getAnnotations()) {
            Annotation cacheAnnotation = method.getAnnotation(annotationType);
            if (null == cacheAnnotation) {
                continue;
            }
            if (annotationType.getGenericSuperclass().equals(ObjCache.class.getGenericSuperclass())) {
                ObjCache objCache = (ObjCache) cacheAnnotation;
                cacheMethodMeta.setPrefix(objCache.prefix());
                cacheMethodMeta.setTimeOut(objCache.timeOut());
                cacheMethodMeta.setTimeUnit(objCache.timeUnit());
            }else if (annotationType.getGenericSuperclass().equals(ObjCacheUpdate.class.getGenericSuperclass())) {
                ObjCacheUpdate objCacheUpdate = (ObjCacheUpdate) cacheAnnotation;
                cacheMethodMeta.setPrefix(objCacheUpdate.prefix());
                cacheMethodMeta.setTimeOut(objCacheUpdate.timeOut());
                cacheMethodMeta.setTimeUnit(objCacheUpdate.timeUnit());
            }else if (annotationType.getGenericSuperclass().equals(ObjCacheDelete.class.getGenericSuperclass())) {
                ObjCacheDelete objCacheDelete = (ObjCacheDelete) cacheAnnotation;
                cacheMethodMeta.setPrefix(objCacheDelete.prefix());
                cacheMethodMeta.setAllEntries(objCacheDelete.allEntries());
            }else if (annotationType.getGenericSuperclass().equals(MapCache.class.getGenericSuperclass())) {
                MapCache mapCache = (MapCache) cacheAnnotation;
                cacheMethodMeta.setPrefix(mapCache.mapName());
                cacheMethodMeta.setTimeOut(mapCache.timeOut());
                cacheMethodMeta.setTimeUnit(mapCache.timeUnit());
            }else if (annotationType.getGenericSuperclass().equals(MapCacheUpdate.class.getGenericSuperclass())) {
                ObjCacheDelete objCacheDelete = (ObjCacheDelete) cacheAnnotation;
                cacheMethodMeta.setPrefix(objCacheDelete.prefix());
                cacheMethodMeta.setAllEntries(objCacheDelete.allEntries());
            }else if (annotationType.getGenericSuperclass().equals(ObjCacheDelete.class.getGenericSuperclass())) {
                ObjCacheDelete objCacheDelete = (ObjCacheDelete) cacheAnnotation;
                cacheMethodMeta.setPrefix(objCacheDelete.prefix());
                cacheMethodMeta.setAllEntries(objCacheDelete.allEntries());
            }
            break;
        }
    }
}
