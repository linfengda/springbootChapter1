package com.lfd.soa.srv.demo.support.redis.cache.interceptor;

import com.lfd.soa.srv.demo.support.redis.cache.annotation.QueryCache;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * 描述: 拦截{@link QueryCache}注解的增强器
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
