package com.lfd.soa.srv.demo.support.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MES系统时间字段
 *
 * @author linfengda
 * @date 2021-03-05 16:23
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BizTimeField {

    /**
     * 业务默认时间
     * @return  默认时间
     */
    String defaultTime() default "1970-01-01 08:00:01";
}
