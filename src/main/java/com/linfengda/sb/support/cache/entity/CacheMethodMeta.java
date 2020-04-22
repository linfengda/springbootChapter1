package com.linfengda.sb.support.cache.entity;

import lombok.Data;

import java.lang.annotation.Annotation;
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
public class CacheMethodMeta {
    /**
     * 原始方法
     */
    private Method method;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 方法返回类型
     */
    private Class<?> returnType;
    /**
     * 缓存前缀
     */
    private String prefix;
    /**
     * 缓存失效时间
     */
    private long timeOut;
    /**
     * 缓存失效时间单位
     */
    TimeUnit timeUnit;
    /**
     * 参数key列表
     */
    private List<CacheKeyMeta> keys;
    /**
     * 是否删除前缀的所有缓存
     * @return
     */
    boolean allEntries;
}
