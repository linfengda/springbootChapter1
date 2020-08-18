package com.linfengda.sb.support.redis.cache.handler;

import com.linfengda.sb.support.redis.cache.entity.RedisSupport;
import lombok.Setter;

/**
 * 描述:
 *
 * @author: linfengda
 * @date: 2020-07-07 10:32
 */
@Setter
public abstract class AbstractCacheHandler implements CacheHandler {
    /**
     * redis操作支持
     */
    protected RedisSupport redisSupport;
}
