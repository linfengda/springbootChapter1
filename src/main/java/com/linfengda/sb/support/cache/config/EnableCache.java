package com.linfengda.sb.support.cache.config;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.*;

/**
 * 描述: 开启缓存注解
 *
 * @author linfengda
 * @create 2020-03-24 17:56
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({CacheImportBeanDefinitionSelector.class, })
public @interface EnableCache {
    /**
     * true     目标对象实现了接口	使用CGLIB代理机制
     * true     目标对象没有接口(只有实现类)	使用CGLIB代理机制
     * false    目标对象实现了接口	使用JDK动态代理机制(代理所有实现了的接口)
     * false	目标对象没有接口(只有实现类)	使用CGLIB代理机制
     * @return
     */
    boolean proxyTargetClass() default false;
    /**
     * 代理模式：JDK/AspectJ
     * @return
     */
    AdviceMode mode() default AdviceMode.PROXY;
    /**
     * 查询注解aop优先级
     * @return
     */
    int queryOrder() default Ordered.LOWEST_PRECEDENCE;
    /**
     * 更新注解aop优先级
     * @return
     */
    int updateOrder() default Ordered.LOWEST_PRECEDENCE;
    /**
     * 删除注解aop优先级
     * @return
     */
    int deleteOrder() default Ordered.LOWEST_PRECEDENCE;
}
