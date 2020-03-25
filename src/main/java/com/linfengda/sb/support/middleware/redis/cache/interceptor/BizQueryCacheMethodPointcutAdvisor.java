package com.linfengda.sb.support.middleware.redis.cache.interceptor;

import com.linfengda.sb.support.middleware.redis.cache.annotation.BizCacheEnable;
import com.linfengda.sb.support.middleware.redis.cache.annotation.BizCacheDel;
import com.linfengda.sb.support.middleware.redis.cache.annotation.BizCachePut;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * 描述: 拦截{@link BizCacheEnable}，{@link BizCachePut}，{@link BizCacheDel}注解的增强器
 *
 * @author linfengda
 * @create 2020-03-24 15:43
 */
public class BizQueryCacheMethodPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    /**
     * 缓存注解静态切入点
     */
    private final BizQueryCacheMethodPointcut pointcut = new BizQueryCacheMethodPointcut();

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
