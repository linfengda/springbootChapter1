package com.lfd.soa.srv.demo.support.apivalidator.interceptor;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * 描述: 拦截api层的增强器
 *
 * @author linfengda
 * @create 2020-03-24 15:43
 */
public class ApiValidatorMethodPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    /**
     * 注解静态切入点
     */
    private final ApiValidatorMethodPointcut pointcut = new ApiValidatorMethodPointcut();

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
