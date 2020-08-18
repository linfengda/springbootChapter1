package com.linfengda.sb.support.redis.config;

import com.linfengda.sb.support.redis.JacksonRedisTemplate;
import com.linfengda.sb.support.redis.RedisDistributedLock;
import com.linfengda.sb.support.redis.cache.entity.RedisSupport;
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
public class RedisSupportClassInitialConfig implements ApplicationContextAware {
    @Getter
    private static RedisSupport redisSupport;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        initRedisSupport(applicationContext);
        CacheHandlerHolder.INSTANCE.initHandlers(redisSupport);
        CacheDataTypeResolverHolder.INSTANCE.initResolver(redisSupport);
    }

    /**
     * 初始化redis操作支持
     */
    private void initRedisSupport(ApplicationContext ctx) {
        redisSupport = new RedisSupport();
        redisSupport.setJacksonRedisTemplate(ctx.getBean(JacksonRedisTemplate.class));
        redisSupport.setRedisDistributedLock(ctx.getBean(RedisDistributedLock.class));
    }
}
