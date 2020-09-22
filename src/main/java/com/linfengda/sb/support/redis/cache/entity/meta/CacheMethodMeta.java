package com.linfengda.sb.support.redis.cache.entity.meta;

import com.linfengda.sb.support.redis.cache.entity.type.CacheAnnotationType;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 缓存方法元数据
 *
 * @author linfengda
 * @create 2020-03-27 15:10
 */
@Data
public class CacheMethodMeta extends CacheQueryMeta {
    /**
     * 原始方法对象
     */
    private Method method;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 缓存操作类型
     */
    private CacheAnnotationType cacheAnnotationType;
    /**
     * 数据类型
     */
    private DataType dataType;
    /**
     * 参数列表
     */
    private List<CacheKeyMeta> methodCacheKeys;
    /**
     * 缓存前缀
     */
    private String prefix;
    /**
     * 缓存失效时间
     */
    private Long timeOut;
    /**
     * 缓存失效时间单位
     */
    TimeUnit timeUnit;
}
