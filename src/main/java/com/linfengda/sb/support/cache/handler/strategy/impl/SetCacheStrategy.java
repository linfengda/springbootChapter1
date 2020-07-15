package com.linfengda.sb.support.cache.handler.strategy.impl;

import com.linfengda.sb.support.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.cache.handler.strategy.AbstractCacheStrategy;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述: set
 *
 * @author: linfengda
 * @date: 2020-07-08 16:23
 */
@Slf4j
public class SetCacheStrategy extends AbstractCacheStrategy {
    @Override
    public Object getCache(CacheParamDTO param) {
        return null;
    }

    @Override
    public void setCache(CacheParamDTO param, Object value) {

    }

    @Override
    public Boolean hasKey(CacheParamDTO param) {
        return null;
    }

    @Override
    public Long getCurrentCacheSize(CacheParamDTO param) {
        return null;
    }
}
