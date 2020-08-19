package com.linfengda.sb.support.redis.config.annotation;

import com.linfengda.sb.support.redis.Constant;
import com.linfengda.sb.support.redis.config.selector.RedisCacheAnnotationConfigSelector;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.*;

/**
 * 描述: 开启redis缓存注解，自动引入redisTemplate，redisDistributedLock，缓存注解
 *
 * @author linfengda
 * @create 2020-03-24 17:56
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RedisCacheAnnotationConfigSelector.class})
public @interface EnableRedisCacheAnnotation {
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
    int queryOrder() default Ordered.LOWEST_PRECEDENCE-3;
    /**
     * 删除注解aop优先级
     * @return
     */
    int deleteOrder() default Ordered.LOWEST_PRECEDENCE-2;
    /**
     * 更新注解aop优先级
     * @return
     */
    int updateOrder() default Ordered.LOWEST_PRECEDENCE;
    /**
     * lru缓存后台清除间隔
     * @return
     */
    long lruInternal() default Constant.DEFAULT_BG_REMOVE_LRU_INTERNAL;
}
