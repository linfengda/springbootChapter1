package com.linfengda.sb.support.redis.cache.handler.resolver;

import com.linfengda.sb.support.redis.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;

/**
 * 描述: 不同数据类型缓存处理
 *
 * @author: linfengda
 * @date: 2020-07-08 16:15
 */
public interface CacheDataTypeResolver {

    /**
     * 支持的redis数据类型
     * @param dataType  数据类型
     * @return
     */
    boolean support(DataType dataType);

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
     * 获取当前缓存大小
     * @param param 缓存参数
     * @return      缓存大小
     */
    Long getCurrentCacheSize(CacheParamDTO param);

    /**
     * 查询缓存中是否存在key
     * @param param 缓存参数
     * @return      true：存在，false：不存在
     */
    Boolean hasKey(CacheParamDTO param);
}
