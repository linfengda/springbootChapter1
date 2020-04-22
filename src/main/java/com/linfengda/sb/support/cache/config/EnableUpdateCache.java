package com.linfengda.sb.support.cache.config;

import com.linfengda.sb.support.cache.config.UpdateCacheImportBeanDefinitionSelector;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.*;

/**
 * 描述: 开启更新缓存注解
 *
 * @author linfengda
 * @create 2020-03-26 11:22
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({UpdateCacheImportBeanDefinitionSelector.class})
public @interface EnableUpdateCache {

    boolean proxyTargetClass() default false;

    AdviceMode mode() default AdviceMode.PROXY;

    int order() default Ordered.LOWEST_PRECEDENCE;
}
