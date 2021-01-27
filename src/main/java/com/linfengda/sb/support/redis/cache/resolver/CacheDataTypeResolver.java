package com.linfengda.sb.support.redis.cache.resolver;

import com.linfengda.sb.support.redis.cache.entity.bo.CacheResultBO;
import com.linfengda.sb.support.redis.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;

/**
 * 描述: 不同数据类型缓存处理
 *
 * @author linfengda
 * @date 2020-08-01 16:15
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
    CacheResultBO getCache(CacheParamDTO param);

    /**
     * 写入缓存
     * @param param 缓存参数
     * @param value 缓存数据
     */
    void setCache(CacheParamDTO param, Object value);

    /**
     * 删除缓存
     * @param param 缓存参数
     */
    void delCache(CacheParamDTO param);

    /**
     * 查询当前是否有缓存空间
     * @param param 缓存参数
     * @return      true：仍有缓存空间，false：没有缓存空间
     */
    boolean hasSize(CacheParamDTO param);

    /**
     * 查询缓存中是否存在key
     * @param param 缓存参数
     * @return      true：存在，false：不存在
     */
    boolean hasKey(CacheParamDTO param);
}
