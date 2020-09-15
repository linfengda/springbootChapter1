package com.linfengda.sb.support.redis.config;

import com.linfengda.sb.support.redis.Constant;
import com.linfengda.sb.support.redis.GenericRedisTemplate;
import com.linfengda.sb.support.redis.RedisDistributedLock;
import com.linfengda.sb.support.redis.cache.RedisCacheBgManager;
import com.linfengda.sb.support.redis.cache.handler.CacheHandlerHolder;
import com.linfengda.sb.support.redis.cache.resolver.CacheDataTypeResolverHolder;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @description: 初始化依赖GenericRedisTemplate的相关操作类
 * @author: linfengda
 * @date: 2020-08-18 18:55
 */
public class RedisCacheInitializer implements ApplicationContextAware {
    private GenericRedisTemplate genericRedisTemplate;
    private RedisDistributedLock redisDistributedLock;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        genericRedisTemplate = applicationContext.getBean(GenericRedisTemplate.class);
        redisDistributedLock = applicationContext.getBean(RedisDistributedLock.class);
        Long lruCacheClearInternal = AnnotationMetaHolder.INSTANCE.getAttributes().<Long>getNumber("lruCacheClearInternal");
        if (null == lruCacheClearInternal) {
            lruCacheClearInternal = Constant.DEFAULT_LRU_CACHE_BG_REMOVE_INTERNAL;
        }
        RedisCacheBgManager.INSTANCE.setGenericRedisTemplate(genericRedisTemplate);
        RedisCacheBgManager.INSTANCE.start(lruCacheClearInternal);
        CacheHandlerHolder.INSTANCE.initHandlers(genericRedisTemplate, redisDistributedLock);
        CacheDataTypeResolverHolder.INSTANCE.initResolver(genericRedisTemplate);
    }
}
