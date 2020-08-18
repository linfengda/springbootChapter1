package com.linfengda.sb.support.redis.cache.handler;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.support.redis.cache.entity.RedisSupport;
import com.linfengda.sb.support.redis.cache.entity.type.CacheAnnotationType;
import com.linfengda.sb.support.redis.cache.handler.impl.DeleteCacheHandler;
import com.linfengda.sb.support.redis.cache.handler.impl.QueryCacheHandler;
import com.linfengda.sb.support.redis.cache.handler.impl.UpdateCacheHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 初始化全部handler
 *
 * @author: linfengda
 * @date: 2020-07-14 16:24
 */
public enum CacheHandlerHolder {
    /**
     * 单例
     */
    INSTANCE;
    private final List<CacheHandler> handlers = new ArrayList<>();

    /**
     * 初始化全部handler
     * @param redisSupport
     */
    public void initHandlers(RedisSupport redisSupport) {
        QueryCacheHandler queryCacheHandler = new QueryCacheHandler();
        queryCacheHandler.setRedisSupport(redisSupport);
        DeleteCacheHandler deleteCacheHandler = new DeleteCacheHandler();
        deleteCacheHandler.setRedisSupport(redisSupport);
        UpdateCacheHandler updateCacheHandler = new UpdateCacheHandler();
        updateCacheHandler.setRedisSupport(redisSupport);
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
