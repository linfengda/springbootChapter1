package com.linfengda.sb.support.cache.config;

import com.linfengda.sb.support.cache.interceptor.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * 描述: 注册缓存configuration
 *
 * @author linfengda
 * @create 2020-03-24 18:38
 */
@SpringBootConfiguration
public class CacheConfig extends AbstractCacheConfig {

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
        if (this.attributes != null) {
            queryCacheMethodPointcutAdvisor.setOrder(this.attributes.<Integer>getNumber("queryOrder"));
        }
        return queryCacheMethodPointcutAdvisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DeleteCacheMethodPointcutAdvisor deleteCacheMethodPointcutAdvisor(DeleteCacheInterceptor deleteQueryCacheInterceptor) {
        DeleteCacheMethodPointcutAdvisor deleteCacheMethodPointcutAdvisor = new DeleteCacheMethodPointcutAdvisor();
        deleteCacheMethodPointcutAdvisor.setAdvice(deleteQueryCacheInterceptor);
        if (this.attributes != null) {
            deleteCacheMethodPointcutAdvisor.setOrder(this.attributes.<Integer>getNumber("deleteOrder"));
        }
        return deleteCacheMethodPointcutAdvisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public UpdateCacheMethodPointcutAdvisor updateCacheMethodPointcutAdvisor(UpdateCacheInterceptor updateQueryCacheInterceptor) {
        UpdateCacheMethodPointcutAdvisor updateCacheMethodPointcutAdvisor = new UpdateCacheMethodPointcutAdvisor();
        updateCacheMethodPointcutAdvisor.setAdvice(updateQueryCacheInterceptor);
        if (this.attributes != null) {
            updateCacheMethodPointcutAdvisor.setOrder(this.attributes.<Integer>getNumber("updateOrder"));
        }
        return updateCacheMethodPointcutAdvisor;
    }
}
