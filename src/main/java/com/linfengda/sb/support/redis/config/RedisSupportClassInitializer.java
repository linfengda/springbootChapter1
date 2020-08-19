package com.linfengda.sb.support.redis.config;

import com.linfengda.sb.support.redis.JacksonRedisTemplate;
import com.linfengda.sb.support.redis.RedisDistributedLock;
import com.linfengda.sb.support.redis.cache.handler.CacheHandlerHolder;
import com.linfengda.sb.support.redis.cache.resolver.CacheDataTypeResolverHolder;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @description: 通过该配置初始化cache需要的相关操作类
 * @author: linfengda
 * @date: 2020-08-18 18:55
 */
public class RedisSupportClassInitializer implements ApplicationContextAware {
    @Getter
    private static JacksonRedisTemplate jacksonRedisTemplate;
    @Getter
    private static RedisDistributedLock redisDistributedLock;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        initRedisSupport(applicationContext);
        CacheHandlerHolder.INSTANCE.initHandlers(jacksonRedisTemplate, redisDistributedLock);
        CacheDataTypeResolverHolder.INSTANCE.initResolver(jacksonRedisTemplate);
    }

    /**
     * 初始化redis操作支持
     */
    private void initRedisSupport(ApplicationContext ctx) {
        jacksonRedisTemplate = ctx.getBean(JacksonRedisTemplate.class);
        redisDistributedLock = ctx.getBean(RedisDistributedLock.class);
    }
}
