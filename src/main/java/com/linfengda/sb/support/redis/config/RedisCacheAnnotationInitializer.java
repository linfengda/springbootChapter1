package com.linfengda.sb.support.redis.config;

import com.linfengda.sb.support.redis.GenericRedisTemplate;
import com.linfengda.sb.support.redis.cache.handler.CacheHandlerHolder;
import com.linfengda.sb.support.redis.cache.resolver.CacheDataTypeResolverHolder;
import com.linfengda.sb.support.redis.lock.RedisDistributedLock;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @description: 初始化依赖GenericRedisTemplate的相关操作类
 * @author: linfengda
 * @date: 2020-08-18 18:55
 */
public class RedisCacheAnnotationInitializer implements ApplicationContextAware {
    private GenericRedisTemplate genericRedisTemplate;
    private RedisDistributedLock redisDistributedLock;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        genericRedisTemplate = applicationContext.getBean(GenericRedisTemplate.class);
        redisDistributedLock = applicationContext.getBean(RedisDistributedLock.class);
        init();
    }

    private void init() {
        CacheHandlerHolder.INSTANCE.initHandlers(genericRedisTemplate, redisDistributedLock);
        CacheDataTypeResolverHolder.INSTANCE.initResolver(genericRedisTemplate);
    }
}
