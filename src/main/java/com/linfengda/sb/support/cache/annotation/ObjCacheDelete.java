package com.linfengda.sb.support.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述: 删除对象类型缓存注解
 *
 * @author linfengda
 * @create 2020-03-24 15:09
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ObjCacheDelete {
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
     * 是否在方法执行前删除
     * @return
     */
    boolean beforeInvocation() default false;
}
