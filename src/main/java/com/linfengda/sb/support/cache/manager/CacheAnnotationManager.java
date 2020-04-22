package com.linfengda.sb.support.cache.manager;

import com.linfengda.sb.support.cache.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

/**
 * 描述: 缓存注解管理
 *
 * @author linfengda
 * @create 2020-03-30 14:32
 */
@Getter
@AllArgsConstructor
public enum CacheAnnotationManager {
    /**
     * 查询缓存注解列表
     */
    QUERY_CACHE_ANNOTATIONS(Arrays.asList(ObjCache.class, MapCache.class)),
    /**
     * 更新缓存注解列表
     */
    UPDATE_CACHE_ANNOTATIONS(Arrays.asList(ObjCacheUpdate.class, ObjCacheDelete.class, MapCacheUpdate.class, MapCacheDelete.class)),
    /**
     * 所有缓存注解列表
     */
    ALL_CACHE_ANNOTATIONS(Arrays.asList(ObjCache.class, MapCache.class, ObjCacheUpdate.class, ObjCacheDelete.class, MapCacheUpdate.class, MapCacheDelete.class));

    /**
     * 注解标签列表
     */
    private List<Class<? extends Annotation>> annotations;
}
