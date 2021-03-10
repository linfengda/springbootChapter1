package com.lfd.soa.srv.demo.support.redis.config;

import com.lfd.soa.srv.demo.support.redis.config.initailizer.BusinessLockAnnotationInitializer;
import com.lfd.soa.srv.demo.support.redis.lock.interceptor.BusinessLockInterceptor;
import com.lfd.soa.srv.demo.support.redis.lock.interceptor.BusinessLockMethodPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationAttributes;

/**
 * @description 配置业务锁注解增强器
 * @author linfengda
 * @date 2020-12-29 22:14
 */
public class BusinessLockAnnotationConfig extends BusinessLockAnnotationInitializer {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public BusinessLockInterceptor businessLockInterceptor() {
        return new BusinessLockInterceptor();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public BusinessLockMethodPointcutAdvisor businessLockMethodPointcutAdvisor(BusinessLockInterceptor businessLockInterceptor) {
        BusinessLockMethodPointcutAdvisor businessLockMethodPointcutAdvisor = new BusinessLockMethodPointcutAdvisor();
        businessLockMethodPointcutAdvisor.setAdvice(businessLockInterceptor);
        AnnotationAttributes attributes = AnnotationAttributeHolder.INSTANCE.getAttributes();
        if (attributes != null) {
            businessLockMethodPointcutAdvisor.setOrder(attributes.<Integer>getNumber("lockOrder"));
        }
        return businessLockMethodPointcutAdvisor;
    }
}
