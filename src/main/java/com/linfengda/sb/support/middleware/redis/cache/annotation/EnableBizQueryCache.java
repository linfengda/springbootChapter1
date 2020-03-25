package com.linfengda.sb.support.middleware.redis.cache.annotation;

import com.linfengda.sb.support.middleware.redis.cache.BizQueryCacheImportBeanDefinitionSelector;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.*;

/**
 * 描述: 注解开启业务查询缓存
 *
 * @author linfengda
 * @create 2020-03-24 17:56
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({BizQueryCacheImportBeanDefinitionSelector.class})
public @interface EnableBizQueryCache {

    boolean proxyTargetClass() default false;

    AdviceMode mode() default AdviceMode.PROXY;

    int order() default Ordered.LOWEST_PRECEDENCE;
}
