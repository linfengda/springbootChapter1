package com.lfd.soa.srv.demo.support.redis.config.initailizer;

import com.lfd.soa.srv.demo.support.redis.lock.holder.BusinessLockHolder;
import com.lfd.soa.srv.demo.support.redis.lock.RedisDistributedLock;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @description 初始化业务锁注解服务的依赖
 * @author linfengda
 * @date 2020-12-29 23:12
 */
public class BusinessLockAnnotationInitializer implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RedisDistributedLock redisDistributedLock = applicationContext.getBean(RedisDistributedLock.class);
        BusinessLockHolder.init(redisDistributedLock);
    }
}
