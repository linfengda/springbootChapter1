package com.linfengda.sb.support.redis.cache.handler;

import com.linfengda.sb.support.redis.JacksonRedisTemplate;
import com.linfengda.sb.support.redis.RedisDistributedLock;
import lombok.Setter;

/**
 * 描述:
 *
 * @author: linfengda
 * @date: 2020-07-07 10:32
 */
@Setter
public abstract class AbstractCacheHandler implements CacheHandler {
    protected JacksonRedisTemplate jacksonRedisTemplate;
    protected RedisDistributedLock redisDistributedLock;
}
