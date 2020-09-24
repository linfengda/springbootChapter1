package com.linfengda.sb.support.redis.cache.annotation;

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
    Cache[] caches() default {};
}
