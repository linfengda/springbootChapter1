package com.linfengda.sb.support.cache.annotation;

import com.linfengda.sb.support.cache.entity.type.DataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述: 删除缓存注解
 *
 * @author linfengda
 * @create 2020-03-24 15:09
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DeleteCache {
    /**
     * 缓存前缀，建议使用方法名
     * @return
     */
    String prefix() default "";
    /**
     * 是否删除前缀的所有缓存
     * @return
     */
    boolean allEntries() default false;
    /**
     * 缓存类型
     */
    DataType type() default DataType.OBJECT;
}
