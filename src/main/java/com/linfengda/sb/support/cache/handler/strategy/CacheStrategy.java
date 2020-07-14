package com.linfengda.sb.support.cache.handler.strategy;

import com.linfengda.sb.support.cache.config.Constant;
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
     * 写入缓存
     * @param param 缓存参数
     * @param value 缓存数据
     */
    void setCache(CacheParamDTO param, Object value);

    /**
     * 查询key是否存在缓存中
     * @param param 缓存参数
     * @return      true：存在，false：不存在
     */
    Boolean hasKey(CacheParamDTO param);

    /**
     * 当前是否已经达到最大缓存
     * @param param 缓存参数
     * @return      true：是，false：否
     */
    default Boolean isMaxCacheSize(CacheParamDTO param) {
        Long maxSize = param.getMaxSize();
        if (Constant.DEFAULT_NO_SIZE_LIMIT.equals(maxSize)) {
            return false;
        }
        Long size = getCurrentCacheSize(param);
        if (maxSize > size) {
            return false;
        }
        return true;
    }

    /**
     * 获取当前缓存大小
     * @param param 缓存参数
     * @return      缓存大小
     */
    Long getCurrentCacheSize(CacheParamDTO param);
}
