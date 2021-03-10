package com.lfd.srv.demo.support.redis.config.initailizer;

import com.lfd.srv.demo.support.redis.GenericRedisTemplate;
import com.lfd.srv.demo.support.redis.cache.handler.CacheHandlerHolder;
import com.lfd.srv.demo.support.redis.cache.resolver.CacheDataTypeResolverHolder;
import com.lfd.srv.demo.support.redis.lock.RedisDistributedLock;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @description 初始化缓存注解服务的依赖
 * @author linfengda
 * @date 2020-08-18 18:55
 */
public class RedisCacheAnnotationInitializer implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        GenericRedisTemplate genericRedisTemplate = applicationContext.getBean(GenericRedisTemplate.class);
        RedisDistributedLock redisDistributedLock = applicationContext.getBean(RedisDistributedLock.class);
        CacheHandlerHolder.INSTANCE.initHandlers(genericRedisTemplate, redisDistributedLock);
        CacheDataTypeResolverHolder.INSTANCE.initResolver(genericRedisTemplate);
    }
}
