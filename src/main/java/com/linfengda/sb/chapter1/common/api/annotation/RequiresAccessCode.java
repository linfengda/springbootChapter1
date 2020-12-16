package com.linfengda.sb.chapter1.common.api.annotation;

import java.lang.annotation.*;

/**
 * @description: 访问权限code注解
 * @author: linfengda
 * @date: 2020-09-19 17:07
 */
@Inherited
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresAccessCode {
    /**
     * 接口所需权限code
     * @return
     */
    String[] value() default "";
}
