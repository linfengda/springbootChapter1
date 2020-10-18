package com.linfengda.sb.support.redis.cache.handler;

import com.linfengda.sb.support.redis.cache.entity.dto.CacheTargetDTO;
import com.linfengda.sb.support.redis.cache.entity.type.CacheAnnotationType;

/**
 * 描述: 缓存委派模式接口
 *
 * @author linfengda
 * @create 2020-07-26 14:10
 */
public interface CacheHandler {
    /**
     * 支持的redis注解类型
     * @param annotationType    注解类型
     * @return
     */
    boolean support(CacheAnnotationType annotationType);

    /**
     * 缓存
     * @param cacheTargetDTO    redis缓存处理对象
     * @return                  查询缓存结果/删除缓存结果/更新缓存结果
     * @throws Throwable
     */
    Object doCache(CacheTargetDTO cacheTargetDTO) throws Throwable;
}
