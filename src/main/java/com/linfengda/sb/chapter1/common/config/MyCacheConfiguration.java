package com.linfengda.sb.chapter1.common.config;

import com.linfengda.sb.support.middleware.redis.cache.interceptor.MyCacheInterceptor;
import com.linfengda.sb.support.middleware.redis.cache.interceptor.MyCacheMethodPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

/**
 * 描述: 方法缓存配置
 *
 * @author linfengda
 * @create 2020-03-24 18:38
 */
public class MyCacheConfiguration {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public MyCacheInterceptor myCacheInterceptor() {
        MyCacheInterceptor myCacheInterceptor = new MyCacheInterceptor();
        return myCacheInterceptor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public MyCacheMethodPointcutAdvisor myCacheMethodPointcutAdvisor(MyCacheInterceptor myCacheInterceptor) {
        MyCacheMethodPointcutAdvisor myCacheMethodPointcutAdvisor = new MyCacheMethodPointcutAdvisor();
        myCacheMethodPointcutAdvisor.setAdvice(myCacheInterceptor);
        return myCacheMethodPointcutAdvisor;
    }
}
