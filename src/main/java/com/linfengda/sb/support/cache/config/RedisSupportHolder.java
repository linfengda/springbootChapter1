package com.linfengda.sb.support.cache.config;

import com.linfengda.sb.support.cache.redis.lock.RedisDistributedLock;
import com.linfengda.sb.support.cache.redis.template.SimpleRedisTemplate;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * 描述: redis操作支持
 *
 * @author: linfengda
 * @date: 2020-07-14 16:24
 */
@SpringBootConfiguration
public class RedisSupportHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(RedisSupportHolder.applicationContext == null){
            RedisSupportHolder.applicationContext  = applicationContext;
        }
    }

    public static Jackson2JsonRedisSerializer<Object> getJackson2JsonRedisSerializer(){
        return applicationContext.getBean(Jackson2JsonRedisSerializer.class);
    }

    public static SimpleRedisTemplate getSimpleRedisTemplate(){
        return applicationContext.getBean(SimpleRedisTemplate.class);
    }

    public static RedisDistributedLock getRedisDistributedLock(){
        return applicationContext.getBean(RedisDistributedLock.class);
    }
}
