package com.linfengda.sb.support.redis.cache.handler;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.support.redis.JacksonRedisTemplate;
import com.linfengda.sb.support.redis.RedisDistributedLock;
import com.linfengda.sb.support.redis.cache.entity.type.CacheAnnotationType;
import com.linfengda.sb.support.redis.cache.handler.impl.DeleteCacheHandler;
import com.linfengda.sb.support.redis.cache.handler.impl.QueryCacheHandler;
import com.linfengda.sb.support.redis.cache.handler.impl.UpdateCacheHandler;
import com.linfengda.sb.support.redis.cache.handler.resolver.CacheDataTypeResolverHolder;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 初始化全部handler
 *
 * @author: linfengda
 * @date: 2020-07-14 16:24
 */
public abstract class CacheHandlerHolder implements ApplicationContextAware {
    private RedisSupport redisSupport = null;
    private final List<CacheHandler> handlers = new ArrayList<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        initRedisSupport(applicationContext);
        initHandlers();
        CacheDataTypeResolverHolder.INSTANCE.initResolver(redisSupport);
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
     * 初始化全部handler
     */
    private void initHandlers() {
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

    /**
     * 获取redis操作支持
     * @return
     */
    protected RedisSupport getRedisSupport() {
        return redisSupport;
    }
}
