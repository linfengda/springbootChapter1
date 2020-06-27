package com.linfengda.sb.support.cache.config;

import com.linfengda.sb.support.cache.interceptor.CacheInterceptor;
import com.linfengda.sb.support.cache.interceptor.DeleteCacheMethodPointcutAdvisor;
import com.linfengda.sb.support.cache.interceptor.QueryCacheMethodPointcutAdvisor;
import com.linfengda.sb.support.cache.interceptor.UpdateCacheMethodPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

/**
 * 描述: 注册缓存configuration
 *
 * @author linfengda
 * @create 2020-03-24 18:38
 */
public class CacheConfiguration {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheInterceptor queryCacheInterceptor() {
        CacheInterceptor cacheInterceptor = new CacheInterceptor();
        return cacheInterceptor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheInterceptor deleteCacheInterceptor() {
        CacheInterceptor cacheInterceptor = new CacheInterceptor();
        return cacheInterceptor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheInterceptor updateCacheInterceptor() {
        CacheInterceptor cacheInterceptor = new CacheInterceptor();
        return cacheInterceptor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public QueryCacheMethodPointcutAdvisor queryCacheMethodPointcutAdvisor(CacheInterceptor queryCacheInterceptor) {
        QueryCacheMethodPointcutAdvisor queryCacheMethodPointcutAdvisor = new QueryCacheMethodPointcutAdvisor();
        queryCacheMethodPointcutAdvisor.setAdvice(queryCacheInterceptor);
        return queryCacheMethodPointcutAdvisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DeleteCacheMethodPointcutAdvisor deleteCacheMethodPointcutAdvisor(CacheInterceptor deleteCacheInterceptor) {
        DeleteCacheMethodPointcutAdvisor deleteCacheMethodPointcutAdvisor = new DeleteCacheMethodPointcutAdvisor();
        deleteCacheMethodPointcutAdvisor.setAdvice(deleteCacheInterceptor);
        return deleteCacheMethodPointcutAdvisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public UpdateCacheMethodPointcutAdvisor updateCacheMethodPointcutAdvisor(CacheInterceptor updateCacheInterceptor) {
        UpdateCacheMethodPointcutAdvisor updateCacheMethodPointcutAdvisor = new UpdateCacheMethodPointcutAdvisor();
        updateCacheMethodPointcutAdvisor.setAdvice(updateCacheInterceptor);
        return updateCacheMethodPointcutAdvisor;
    }
}
