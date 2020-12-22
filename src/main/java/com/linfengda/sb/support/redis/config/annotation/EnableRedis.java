package com.linfengda.sb.support.redis.config.annotation;

import com.linfengda.sb.support.redis.config.selector.RedisConfigSelector;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.*;

/**
 * @description: 开启redis，自动引入redisTemplate，redisDistributedLock，redis缓存等
 * @author: linfengda
 * @date: 2020-07-26 22:34
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RedisConfigSelector.class})
public @interface EnableRedis {
    /**
     * 是否启用注解缓存
     * @return
     */
    boolean openCacheAnnotation() default false;
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
}
