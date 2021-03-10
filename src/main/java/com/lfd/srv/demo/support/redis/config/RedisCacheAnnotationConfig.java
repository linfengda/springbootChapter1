package com.lfd.srv.demo.support.redis.config;

import com.lfd.srv.demo.support.redis.cache.interceptor.*;
import com.lfd.srv.demo.support.redis.config.initailizer.RedisCacheAnnotationInitializer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationAttributes;

/**
 * 描述: 配置redis缓存注解增强器
 *
 * @author linfengda
 * @create 2020-03-24 18:38
 */
public class RedisCacheAnnotationConfig extends RedisCacheAnnotationInitializer {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public QueryCacheInterceptor queryCacheInterceptor() {
        return new QueryCacheInterceptor();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DeleteCacheInterceptor deleteCacheInterceptor() {
        return new DeleteCacheInterceptor();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public UpdateCacheInterceptor updateCacheInterceptor() {
        return new UpdateCacheInterceptor();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public QueryCacheMethodPointcutAdvisor queryCacheMethodPointcutAdvisor(QueryCacheInterceptor queryCacheInterceptor) {
        QueryCacheMethodPointcutAdvisor queryCacheMethodPointcutAdvisor = new QueryCacheMethodPointcutAdvisor();
        queryCacheMethodPointcutAdvisor.setAdvice(queryCacheInterceptor);
        AnnotationAttributes attributes = AnnotationAttributeHolder.INSTANCE.getAttributes();
        if (attributes != null) {
            queryCacheMethodPointcutAdvisor.setOrder(attributes.<Integer>getNumber("queryOrder"));
        }
        return queryCacheMethodPointcutAdvisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DeleteCacheMethodPointcutAdvisor deleteCacheMethodPointcutAdvisor(DeleteCacheInterceptor deleteCacheInterceptor) {
        DeleteCacheMethodPointcutAdvisor deleteCacheMethodPointcutAdvisor = new DeleteCacheMethodPointcutAdvisor();
        deleteCacheMethodPointcutAdvisor.setAdvice(deleteCacheInterceptor);
        AnnotationAttributes attributes = AnnotationAttributeHolder.INSTANCE.getAttributes();
        if (attributes != null) {
            deleteCacheMethodPointcutAdvisor.setOrder(attributes.<Integer>getNumber("deleteOrder"));
        }
        return deleteCacheMethodPointcutAdvisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public UpdateCacheMethodPointcutAdvisor updateCacheMethodPointcutAdvisor(UpdateCacheInterceptor updateCacheInterceptor) {
        UpdateCacheMethodPointcutAdvisor updateCacheMethodPointcutAdvisor = new UpdateCacheMethodPointcutAdvisor();
        updateCacheMethodPointcutAdvisor.setAdvice(updateCacheInterceptor);
        AnnotationAttributes attributes = AnnotationAttributeHolder.INSTANCE.getAttributes();
        if (attributes != null) {
            updateCacheMethodPointcutAdvisor.setOrder(attributes.<Integer>getNumber("updateOrder"));
        }
        return updateCacheMethodPointcutAdvisor;
    }
}
