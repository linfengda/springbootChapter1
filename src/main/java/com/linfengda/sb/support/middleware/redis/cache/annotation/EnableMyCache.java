package com.linfengda.sb.support.middleware.redis.cache.annotation;

import com.linfengda.sb.support.middleware.redis.cache.MyCacheImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.*;

/**
 * 描述: 注解开启方法缓存
 *
 * @author linfengda
 * @create 2020-03-24 17:56
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MyCacheImportBeanDefinitionRegistrar.class})
public @interface EnableMyCache {

    boolean proxyTargetClass() default false;

    AdviceMode mode() default AdviceMode.PROXY;

    int order() default Ordered.LOWEST_PRECEDENCE;
}
