package com.linfengda.sb.chapter1.config;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 描述: 熔断器配置
 *
 * @author linfengda
 * @create 2019-04-04 14:41
 */
@SpringBootConfiguration
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
