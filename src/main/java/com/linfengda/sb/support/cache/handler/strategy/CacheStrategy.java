package com.linfengda.sb.support.cache.handler.strategy;

import com.linfengda.sb.support.cache.entity.dto.CacheParamDTO;

/**
 * 描述: 不同数据类型缓存策略
 *
 * @author: linfengda
 * @date: 2020-07-08 16:15
 */
public interface CacheStrategy {
    /**
     * 查询缓存
     * @param param 缓存参数
     * @return      缓存数据
     */
    Object getCache(CacheParamDTO param);

    /**
     * 缓存
     * @param param 缓存参数
     * @param value 缓存数据
     */
    void setCache(CacheParamDTO param, Object value);
}
