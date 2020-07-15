package com.linfengda.sb.support.cache.annotation;

import java.lang.annotation.*;

/**
 * 描述: 缓存key注解
 *
 * @author linfengda
 * @create 2020-03-27 15:05
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface CacheKey {
    /**
     * key为空时使用值
     * @return
     */
    String nullKey() default "";
}
