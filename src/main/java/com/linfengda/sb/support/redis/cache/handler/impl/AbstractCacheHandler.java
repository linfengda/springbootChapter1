package com.linfengda.sb.support.redis.cache.handler.impl;

import com.linfengda.sb.support.redis.cache.entity.dto.CacheTargetDTO;
import com.linfengda.sb.support.redis.cache.handler.CacheHandler;
import com.linfengda.sb.support.redis.cache.handler.RedisSupport;
import com.linfengda.sb.support.redis.cache.handler.resolver.CacheDataTypeResolverComposite;
import lombok.Getter;

/**
 * 描述:
 *
 * @author: linfengda
 * @date: 2020-07-07 10:32
 */
@Getter
public abstract class AbstractCacheHandler implements CacheHandler {
    /**
     * redis操作支持
     */
    private RedisSupport redisSupport;
    /**
     * redis数据类型支持
     */
    private CacheDataTypeResolverComposite cacheDataTypeResolverComposite;
    /**
     * redis缓存处理对象
     */
    private CacheTargetDTO cacheTargetDTO;

    public AbstractCacheHandler(CacheTargetDTO cacheTargetDTO) {
        this.cacheTargetDTO = cacheTargetDTO;
    }

    @Override
    public void init(RedisSupport redisSupport) {
        this.redisSupport = redisSupport;
        this.cacheDataTypeResolverComposite = new CacheDataTypeResolverComposite();
        this.cacheDataTypeResolverComposite.init(redisSupport);
    }
}
