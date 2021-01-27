package com.linfengda.sb.support.gateway.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 访问权限code注解
 * @author linfengda
 * @date 2020-09-19 17:07
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@LoginRequired
public @interface Permission {
    /**
     * 接口所需权限code
     * @return
     */
    String[] value() default "";
}

