package com.linfengda.sb.chapter1.hystrix.service.impl;

import com.linfengda.sb.support.exception.BusinessException;
import com.linfengda.sb.chapter1.hystrix.service.HystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 描述: 熔断测试服务
 *
 * @author linfengda
 * @create 2019-04-04 16:38
 */
@Slf4j
@Service
public class HystrixServiceImpl implements HystrixService {


    @HystrixCommand(fallbackMethod = "hysOverThreadFallback",
                    threadPoolKey = "threadPool1",
                    threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "10")},
                    commandProperties = {
                    @HystrixProperty(name = "execution.timeout.enabled", value = "false"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30000"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "60000")})
    @Async("myExecutorPool")
    @Override
    public void hysOverThread(Integer threadId) throws Exception {
        log.info("-------------------------执行方法[hysOverThread]");
        Thread.sleep(100);
    }

    public void hysOverThreadFallback(Integer threadId) throws Exception {
        log.warn("-------------------------异步方法线程数>hystrix线程数，服务降级，threadId={}", threadId);
    }


    @HystrixCommand(fallbackMethod = "hysWithThreadPoolKeyFallback",
                    threadPoolKey = "threadPool2",
                    threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "10")},
                    commandProperties = {
                    @HystrixProperty(name = "execution.timeout.enabled", value = "false"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30000"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "60000")})
    @Async("myExecutorPool")
    @Override
    public void hysWithThreadPoolKey(Integer threadId) throws Exception {
        log.info("-------------------------执行方法[hysWithThreadPoolKey]");
        Thread.sleep(100);
    }

    public void hysWithThreadPoolKeyFallback(Integer threadId) throws Exception {
        log.warn("-------------------------异步方法线程数>hystrix线程数，服务降级，threadId={}", threadId);
    }


    @HystrixCommand(fallbackMethod = "hysTimeOutFallback",
                    commandProperties = {
                    @HystrixProperty(name = "execution.timeout.enabled", value = "true"),
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30000"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "60000")})
    @Override
    public void hysTimeOut() throws Exception {
        log.info("-------------------------执行方法[hysTimeOut]");
        Thread.sleep(4000L);
    }

    public void hysTimeOutFallback() throws Exception {
        log.warn("-------------------------方法执行超时，服务降级");
    }

    @HystrixCommand(fallbackMethod = "hysOverVolumeThresholdFallback",
                    ignoreExceptions = NullPointerException.class,
                    threadPoolKey = "myThreadPool",
                    threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "100"),
                    @HystrixProperty(name = "maxQueueSize", value = "100")},
                    commandProperties = {
                    // 超时不熔断
                    @HystrixProperty(name = "execution.timeout.enabled", value = "false"),
                    // 设置统计请求的窗口大小
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
                    // 设置统计请求的桶数量（实际统计间隔不走这个参数）
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "10"),
                    // 设置统计请求的桶大小（实际统计间隔不走这个参数）
                    @HystrixProperty(name = "metrics.healthSnapshot.intervalInMilliseconds", value = "500"),
                    // 在窗口时间内失败次数>5启用熔断
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    // 在窗口时间内失败比例>50%启用熔断
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    // 熔断后30s进行重试
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30000")})
    @Override
    public void hysOverVolumeThreshold(Long requestId, Boolean isError) throws Exception {
        log.info("-------------------------第{}次请求，执行方法[hysOverVolumeThreshold]", requestId);
        if (isError) {
            throw new BusinessException("第"+requestId+"次请求，发生异常");
        }
    }

    public void hysOverVolumeThresholdFallback(Long requestId, Boolean isError) throws Exception {
        log.warn("-------------------------第{}次请求，服务降级", requestId);
    }

}
