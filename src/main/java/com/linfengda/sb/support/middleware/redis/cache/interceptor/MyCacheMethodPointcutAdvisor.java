package com.linfengda.sb.support.middleware.redis.cache.interceptor;

import com.linfengda.sb.support.middleware.redis.cache.annotation.CacheEnable;
import com.linfengda.sb.support.middleware.redis.cache.annotation.CacheEvict;
import com.linfengda.sb.support.middleware.redis.cache.annotation.CachePut;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * 描述: 拦截{@link CacheEnable}，{@link CachePut}，{@link CacheEvict}注解的增强器
 *
 * @author linfengda
 * @create 2020-03-24 15:43
 */
public class MyCacheMethodPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    /**
     * 缓存注解静态切入点
     */
    private final MyCacheMethodPointcut pointcut = new MyCacheMethodPointcut();

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
