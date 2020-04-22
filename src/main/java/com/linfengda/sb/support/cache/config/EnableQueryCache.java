package com.linfengda.sb.support.cache.config;

import com.linfengda.sb.support.cache.config.QueryCacheImportBeanDefinitionSelector;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.*;

/**
 * 描述: 开启查询缓存注解
 *
 * @author linfengda
 * @create 2020-03-24 17:56
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({QueryCacheImportBeanDefinitionSelector.class})
public @interface EnableQueryCache {

    boolean proxyTargetClass() default false;

    AdviceMode mode() default AdviceMode.PROXY;

    int order() default Ordered.LOWEST_PRECEDENCE;
}
