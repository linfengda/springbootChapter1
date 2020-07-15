package com.linfengda.sb.support.cache.handler.strategy;

import com.linfengda.sb.support.cache.config.Constant;
import com.linfengda.sb.support.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.cache.entity.type.CacheExtraStrategy;
import com.linfengda.sb.support.cache.entity.type.CacheSizeStrategy;

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
     * 写入缓存
     * @param param 缓存参数
     * @param value 缓存数据
     */
    void setCache(CacheParamDTO param, Object value);

    /**
     * 查询缓存中是否存在key
     * @param param 缓存参数
     * @return      true：存在，false：不存在
     */
    Boolean hasKey(CacheParamDTO param);

    /**
     * 获取当前缓存最大数量策略类型
     * @param param 缓存参数
     * @return      缓存最大数量策略类型 {@link CacheSizeStrategy}
     */
    default CacheSizeStrategy getCacheSizeStrategy(CacheParamDTO param) {
        Long maxSize = param.getMaxSize();
        if (Constant.DEFAULT_NO_SIZE_LIMIT.equals(maxSize)) {
            return CacheSizeStrategy.NORMAL;
        }
        if (maxSize > getCurrentCacheSize(param)) {
            return CacheSizeStrategy.NORMAL;
        }
        if (param.getStrategies().contains(CacheExtraStrategy.MAX_SIZE_STRATEGY_LRU)) {
            return CacheSizeStrategy.LRU;
        }
        return CacheSizeStrategy.ABANDON;
    }

    /**
     * 获取当前缓存大小
     * @param param 缓存参数
     * @return      缓存大小
     */
    Long getCurrentCacheSize(CacheParamDTO param);
}
