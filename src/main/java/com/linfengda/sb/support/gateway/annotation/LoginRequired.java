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
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {

    /**
     * 是否需要登录才能访问，true：是、false：否
     * 当某个controller的方法想跳过权限验证时可标注为false
     * @return
     */
    boolean required() default true;
}
