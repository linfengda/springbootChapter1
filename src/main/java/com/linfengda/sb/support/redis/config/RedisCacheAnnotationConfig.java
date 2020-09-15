package com.linfengda.sb.support.redis.config;

import com.linfengda.sb.support.redis.cache.interceptor.*;
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
public class RedisCacheAnnotationConfig {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public QueryCacheInterceptor queryCacheInterceptor() {
        QueryCacheInterceptor queryCacheInterceptor = new QueryCacheInterceptor();
        return queryCacheInterceptor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DeleteCacheInterceptor deleteCacheInterceptor() {
        DeleteCacheInterceptor deleteCacheInterceptor = new DeleteCacheInterceptor();
        return deleteCacheInterceptor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public UpdateCacheInterceptor updateCacheInterceptor() {
        UpdateCacheInterceptor updateCacheInterceptor = new UpdateCacheInterceptor();
        return updateCacheInterceptor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public QueryCacheMethodPointcutAdvisor queryCacheMethodPointcutAdvisor(QueryCacheInterceptor queryCacheInterceptor) {
        QueryCacheMethodPointcutAdvisor queryCacheMethodPointcutAdvisor = new QueryCacheMethodPointcutAdvisor();
        queryCacheMethodPointcutAdvisor.setAdvice(queryCacheInterceptor);
        AnnotationAttributes attributes = AnnotationMetaHolder.INSTANCE.getAttributes();
        if (attributes != null) {
            queryCacheMethodPointcutAdvisor.setOrder(attributes.<Integer>getNumber("queryOrder"));
        }
        return queryCacheMethodPointcutAdvisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DeleteCacheMethodPointcutAdvisor deleteCacheMethodPointcutAdvisor(DeleteCacheInterceptor deleteQueryCacheInterceptor) {
        DeleteCacheMethodPointcutAdvisor deleteCacheMethodPointcutAdvisor = new DeleteCacheMethodPointcutAdvisor();
        deleteCacheMethodPointcutAdvisor.setAdvice(deleteQueryCacheInterceptor);
        AnnotationAttributes attributes = AnnotationMetaHolder.INSTANCE.getAttributes();
        if (attributes != null) {
            deleteCacheMethodPointcutAdvisor.setOrder(attributes.<Integer>getNumber("deleteOrder"));
        }
        return deleteCacheMethodPointcutAdvisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public UpdateCacheMethodPointcutAdvisor updateCacheMethodPointcutAdvisor(UpdateCacheInterceptor updateQueryCacheInterceptor) {
        UpdateCacheMethodPointcutAdvisor updateCacheMethodPointcutAdvisor = new UpdateCacheMethodPointcutAdvisor();
        updateCacheMethodPointcutAdvisor.setAdvice(updateQueryCacheInterceptor);
        AnnotationAttributes attributes = AnnotationMetaHolder.INSTANCE.getAttributes();
        if (attributes != null) {
            updateCacheMethodPointcutAdvisor.setOrder(attributes.<Integer>getNumber("updateOrder"));
        }
        return updateCacheMethodPointcutAdvisor;
    }
}
