package com.linfengda.sb.support.redis.cache.interceptor;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * 描述: 拦截{@link com.linfengda.sb.support.redis.cache.annotation.QueryCache}注解的增强器
 *
 * @author linfengda
 * @create 2020-03-24 15:43
 */
public class QueryCacheMethodPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    /**
     * 缓存注解静态切入点
     */
    private final QueryCacheMethodPointcut pointcut = new QueryCacheMethodPointcut();

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
