package com.linfengda.sb.chapter1.common.config;

import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述: 熔断器配置
 *
 * @author linfengda
 * @create 2019-04-04 14:41
 */
@Configuration
public class HystrixConfig {

    /**
     * AOP拦截HystrixCommand注解
     * @return
     */
    @Bean
    public HystrixCommandAspect hystrixAspect() {
        //HystrixCommandProperties.Setter().withExecutionTimeoutEnabled(false).withExecutionTimeoutInMilliseconds(1000);
        //HystrixThreadPoolProperties.Setter().withCoreSize(100).withMaximumSize(200).withAllowMaximumSizeToDivergeFromCoreSize(true);
        return new HystrixCommandAspect();
    }
}
