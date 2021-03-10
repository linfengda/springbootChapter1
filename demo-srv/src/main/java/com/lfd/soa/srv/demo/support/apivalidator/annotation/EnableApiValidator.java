package com.lfd.soa.srv.demo.support.apivalidator.annotation;

import com.lfd.soa.srv.demo.support.apivalidator.config.ApiValidatorConfigSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启api层统一校验注解
 * @author linfengda
 * @date 2020-07-26 22:34
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ApiValidatorConfigSelector.class})
public @interface EnableApiValidator {
    /**
     * 后续可以支持pattern，限制包路径提高效率
     * @return
     */
    String pattern() default "";
}
