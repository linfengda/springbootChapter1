package com.linfengda.sb.support.redis.lock.interceptor;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * 描述: 拦截业务锁的增强器
 *
 * @author linfengda
 * @create 2020-03-24 15:43
 */
public class BusinessLockMethodPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    /**
     * 注解静态切入点
     */
    private final BusinessLockMethodPointcut pointcut = new BusinessLockMethodPointcut();

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
