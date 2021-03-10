package com.lfd.srv.demo.support.redis.cache.annotation;

import com.lfd.srv.demo.support.redis.cache.entity.type.DataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description
 * @author linfengda
 * @date 2020-09-27 10:32
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    /**
     * 缓存类型
     */
    DataType type() default DataType.OBJECT;
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
}
