package com.linfengda.sb.support.redis.cache.handler;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.support.redis.GenericRedisTemplate;
import com.linfengda.sb.support.redis.cache.entity.type.CacheAnnotationType;
import com.linfengda.sb.support.redis.cache.handler.impl.DeleteCacheHandler;
import com.linfengda.sb.support.redis.cache.handler.impl.QueryCacheHandler;
import com.linfengda.sb.support.redis.cache.handler.impl.UpdateCacheHandler;
import com.linfengda.sb.support.redis.lock.RedisDistributedLock;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 初始化全部handler
 *
 * @author linfengda
 * @date 2020-07-26 16:24
 */
public enum CacheHandlerHolder {
    /**
     * 单例
     */
    INSTANCE;
    private final List<CacheHandler> handlers = new ArrayList<>();

    /**
     * 初始化全部handler
     * @param genericRedisTemplate
     * @param redisDistributedLock
     */
    public void initHandlers(GenericRedisTemplate genericRedisTemplate, RedisDistributedLock redisDistributedLock) {
        QueryCacheHandler queryCacheHandler = new QueryCacheHandler();
        queryCacheHandler.setGenericRedisTemplate(genericRedisTemplate);
        queryCacheHandler.setRedisDistributedLock(redisDistributedLock);
        DeleteCacheHandler deleteCacheHandler = new DeleteCacheHandler();
        deleteCacheHandler.setGenericRedisTemplate(genericRedisTemplate);
        deleteCacheHandler.setRedisDistributedLock(redisDistributedLock);
        UpdateCacheHandler updateCacheHandler = new UpdateCacheHandler();
        updateCacheHandler.setGenericRedisTemplate(genericRedisTemplate);
        updateCacheHandler.setRedisDistributedLock(redisDistributedLock);
        handlers.add(queryCacheHandler);
        handlers.add(deleteCacheHandler);
        handlers.add(updateCacheHandler);
    }

    /**
     * 根据注解类型获取handler
     * @param annotationType    注解类型
     * @return                  handler
     */
    public CacheHandler getHandler(CacheAnnotationType annotationType) {
        for (CacheHandler handler : handlers) {
            if (handler.support(annotationType)) {
                return handler;
            }
        }
        throw new BusinessException("不支持的缓存注解类型！");
    }
}
