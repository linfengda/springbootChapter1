package com.linfengda.sb.support.middleware.redis.cache;

import com.linfengda.sb.support.middleware.redis.cache.interceptor.BizQueryCacheInterceptor;
import com.linfengda.sb.support.middleware.redis.cache.interceptor.BizQueryCacheMethodPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

/**
 * 描述: 业务查询缓存配置
 *
 * @author linfengda
 * @create 2020-03-24 18:38
 */
public class BizQueryCacheConfiguration {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public BizQueryCacheInterceptor bizQueryCacheInterceptor() {
        BizQueryCacheInterceptor bizQueryCacheInterceptor = new BizQueryCacheInterceptor();
        return bizQueryCacheInterceptor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public BizQueryCacheMethodPointcutAdvisor bizQueryCacheMethodPointcutAdvisor(BizQueryCacheInterceptor bizQueryCacheInterceptor) {
        BizQueryCacheMethodPointcutAdvisor bizQueryCacheMethodPointcutAdvisor = new BizQueryCacheMethodPointcutAdvisor();
        bizQueryCacheMethodPointcutAdvisor.setAdvice(bizQueryCacheInterceptor);
        return bizQueryCacheMethodPointcutAdvisor;
    }
}
