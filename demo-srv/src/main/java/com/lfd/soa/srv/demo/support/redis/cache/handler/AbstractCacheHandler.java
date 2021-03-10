package com.lfd.soa.srv.demo.support.redis.cache.handler;

import com.lfd.soa.srv.demo.support.redis.GenericRedisTemplate;
import com.lfd.soa.srv.demo.support.redis.lock.RedisDistributedLock;
import lombok.Setter;

/**
 * 描述:
 *
 * @author linfengda
 * @date 2020-07-26 10:32
 */
public abstract class AbstractCacheHandler implements CacheHandler {
    @Setter
    protected GenericRedisTemplate genericRedisTemplate;
    @Setter
    protected RedisDistributedLock redisDistributedLock;
}
