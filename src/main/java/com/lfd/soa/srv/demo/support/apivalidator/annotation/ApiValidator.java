package com.lfd.soa.srv.demo.support.apivalidator.annotation;

import java.lang.annotation.*;

/**
 * 描述: 统一api层校验注解
 *
 * @author linfengda
 * @create 2020-03-27 15:05
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiValidator {
}
