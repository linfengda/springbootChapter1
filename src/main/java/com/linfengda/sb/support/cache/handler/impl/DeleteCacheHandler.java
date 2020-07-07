package com.linfengda.sb.support.cache.handler.impl;

import com.linfengda.sb.support.cache.entity.dto.CacheDataDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述: 删除缓存注解处理 handler
 *
 * @author: linfengda
 * @date: 2020-07-07 11:00
 */
@Slf4j
public class DeleteCacheHandler extends AbstractCacheHandler {

    public DeleteCacheHandler(CacheDataDTO cacheDataDTO) {
        super(cacheDataDTO);
    }

    @Override
    public Object doCache() throws Throwable {
        return null;
    }
}