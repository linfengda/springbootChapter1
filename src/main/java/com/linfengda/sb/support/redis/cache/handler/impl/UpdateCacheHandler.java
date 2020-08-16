package com.linfengda.sb.support.redis.cache.handler.impl;

import com.linfengda.sb.support.redis.cache.entity.dto.CacheTargetDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述: 更新缓存注解处理 handler
 *
 * @author: linfengda
 * @date: 2020-07-07 11:00
 */
@Slf4j
public class UpdateCacheHandler extends AbstractCacheHandler {

    public UpdateCacheHandler(CacheTargetDTO cacheTargetDTO) {
        super(cacheTargetDTO);
    }

    @Override
    public Object doCache() throws Throwable {
        return null;
    }
}
