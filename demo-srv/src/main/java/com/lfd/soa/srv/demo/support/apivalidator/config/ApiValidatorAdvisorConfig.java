package com.lfd.soa.srv.demo.support.apivalidator.config;

import com.lfd.soa.srv.demo.support.apivalidator.interceptor.ApiValidatorMethodPointcutAdvisor;
import com.lfd.soa.srv.demo.support.apivalidator.interceptor.ApiValidatorInterceptor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

/**
 * 描述: 配置api增强器
 *
 * @author linfengda
 * @create 2018-09-10 17:00
 */
public class ApiValidatorAdvisorConfig {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ApiValidatorInterceptor apiValidatorInterceptor() {
        return new ApiValidatorInterceptor();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ApiValidatorMethodPointcutAdvisor apiValidatorMethodPointcutAdvisor(ApiValidatorInterceptor apiValidatorInterceptor) {
        ApiValidatorMethodPointcutAdvisor apiValidatorMethodPointcutAdvisor = new ApiValidatorMethodPointcutAdvisor();
        apiValidatorMethodPointcutAdvisor.setAdvice(apiValidatorInterceptor);
        return apiValidatorMethodPointcutAdvisor;
    }
}
