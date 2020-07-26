package com.linfengda.sb.support.redis.cache.interceptor;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * 描述: 拦截{@link com.linfengda.sb.support.redis.cache.annotation.DeleteCache}注解的增强器
 * @author: linfengda
 * @date: 2020-06-27 11:26
 */
public class DeleteCacheMethodPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    /**
     * 缓存注解静态切入点
     */
    private final DeleteCacheMethodPointcut pointcut = new DeleteCacheMethodPointcut();

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
