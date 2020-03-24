package com.linfengda.sb.support.middleware.redis.cache.annotation;

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
public @interface CacheEvict {
    /**
     * 缓存前缀，建议使用方法名
     * @return
     */
    String prefix() default "";
    /**
     * 缓存key列表，对应方法参数名称
     * @return
     */
    String[] keys() default {""};
    /**
     * 是否删除前缀的所有缓存
     * @return
     */
    boolean allEntries() default false;
    /**
     * 是否在方法执行前删除
     * @return
     */
    boolean beforeInvocation() default false;
}
