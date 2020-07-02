package com.linfengda.sb.support.cache.interceptor;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * 描述: 拦截{@link com.linfengda.sb.support.cache.annotation.UpdateCache}注解的增强器
 *
 * @author: linfengda
 * @date: 2020-06-27 11:23
 */
public class UpdateCacheMethodPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    /**
     * 缓存注解静态切入点
     */
    private final UpdateCacheMethodPointcut pointcut = new UpdateCacheMethodPointcut();

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
