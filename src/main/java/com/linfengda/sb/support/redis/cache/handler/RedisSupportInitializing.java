package com.linfengda.sb.support.redis.cache.handler;

import com.linfengda.sb.support.redis.JacksonRedisTemplate;
import com.linfengda.sb.support.redis.RedisDistributedLock;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 描述: redis操作支持
 *
 * @author: linfengda
 * @date: 2020-07-14 16:24
 */
public abstract class RedisSupportInitializing implements ApplicationContextAware {
    private RedisSupport redisSupport = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        initRedisSupport(applicationContext);
    }

    /**
     * 初始化redis操作支持
     */
    private void initRedisSupport(ApplicationContext ctx) {
        redisSupport = new RedisSupport();
        redisSupport.setJacksonRedisTemplate(ctx.getBean(JacksonRedisTemplate.class));
        redisSupport.setRedisDistributedLock(ctx.getBean(RedisDistributedLock.class));
    }

    /**
     * 获取redis操作支持
     * @return
     */
    protected RedisSupport getRedisSupport() {
        return redisSupport;
    }
}
