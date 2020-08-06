package com.linfengda.sb.support.redis.config;

import com.linfengda.sb.support.redis.lock.RedisDistributedLock;
import com.linfengda.sb.support.redis.template.SimpleRedisTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 描述: redis操作支持
 *
 * @author: linfengda
 * @date: 2020-07-14 16:24
 */
public class RedisSupportConfig implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(RedisSupportConfig.applicationContext == null){
            RedisSupportConfig.applicationContext  = applicationContext;
        }
    }

    public static SimpleRedisTemplate getSimpleRedisTemplate(){
        return applicationContext.getBean(SimpleRedisTemplate.class);
    }

    public static RedisDistributedLock getRedisDistributedLock(){
        return applicationContext.getBean(RedisDistributedLock.class);
    }
}
