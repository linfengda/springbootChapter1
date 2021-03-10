package com.lfd.soa.srv.demo.support.redis.cache.interceptor;

import com.lfd.soa.srv.demo.support.redis.cache.annotation.UpdateCache;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * 描述: 拦截{@link UpdateCache}注解的增强器
 *
 * @author linfengda
 * @date 2020-06-27 11:23
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
