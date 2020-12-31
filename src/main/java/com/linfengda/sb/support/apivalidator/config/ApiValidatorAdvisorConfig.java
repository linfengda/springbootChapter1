package com.linfengda.sb.support.apivalidator.config;

import com.linfengda.sb.support.apivalidator.annotation.EnableApiValidator;
import com.linfengda.sb.support.apivalidator.interceptor.ApiValidatorInterceptor;
import com.linfengda.sb.support.apivalidator.interceptor.ApiValidatorMethodPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 描述: 配置api增强器
 *
 * @author linfengda
 * @create 2018-09-10 17:00
 */
@Configuration
public class ApiValidatorAdvisorConfig implements ImportAware {
    protected AnnotationAttributes attributes;

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        this.attributes = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableApiValidator.class.getName(), false));
        if (this.attributes == null) {
            throw new IllegalArgumentException(
                    "@EnableApiValidator is not present on importing class " + importMetadata.getClassName());
        }
    }


    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ApiValidatorInterceptor apiValidatorInterceptor() {
        ApiValidatorInterceptor apiValidatorInterceptor = new ApiValidatorInterceptor();
        return apiValidatorInterceptor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ApiValidatorMethodPointcutAdvisor apiValidatorMethodPointcutAdvisor(ApiValidatorInterceptor apiValidatorInterceptor) {
        ApiValidatorMethodPointcutAdvisor apiValidatorMethodPointcutAdvisor = new ApiValidatorMethodPointcutAdvisor();
        apiValidatorMethodPointcutAdvisor.setAdvice(apiValidatorInterceptor);
        return apiValidatorMethodPointcutAdvisor;
    }
}
