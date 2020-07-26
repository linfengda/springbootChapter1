package com.linfengda.sb.support.redis.cache.handler.impl;

import com.linfengda.sb.support.redis.cache.entity.dto.CacheDataDTO;
import com.linfengda.sb.support.redis.cache.handler.CacheHandler;
import lombok.Data;

/**
 * 描述:
 *
 * @author: linfengda
 * @date: 2020-07-07 10:32
 */
@Data
public abstract class AbstractCacheHandler implements CacheHandler {
    private CacheDataDTO cacheDataDTO;

    public AbstractCacheHandler(CacheDataDTO cacheDataDTO) {
        this.cacheDataDTO = cacheDataDTO;
    }
}
