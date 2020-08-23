package com.linfengda.sb.support.fastjson.annotation;

import com.linfengda.sb.support.fastjson.selector.FastJsonMessageConvectorConfigSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @description: 开启fastJson字段转换注解
 * @author: linfengda
 * @date: 2020-07-26 22:34
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({FastJsonMessageConvectorConfigSelector.class})
public @interface EnableFastJsonMessageConvector {
}
