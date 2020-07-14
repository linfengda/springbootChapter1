package com.linfengda.sb.support.cache.handler.strategy.impl;

import com.linfengda.sb.support.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.cache.handler.strategy.CacheStrategy;

/**
 * 描述: set
 *
 * @author: linfengda
 * @date: 2020-07-08 16:23
 */
public class SetCacheStrategy implements CacheStrategy {
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
}
