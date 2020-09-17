package com.linfengda.sb.support.redis.cache.entity.dto;

import com.linfengda.sb.support.redis.cache.entity.meta.CacheDeleteMeta;
import com.linfengda.sb.support.redis.cache.entity.meta.CacheQueryMeta;
import com.linfengda.sb.support.redis.cache.entity.meta.CacheUpdateMeta;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import com.linfengda.sb.support.redis.util.CacheUtil;
import lombok.Data;

/**
 * 描述: 缓存查询参数DTO
 *
 * @author: linfengda
 * @date: 2020-07-10 14:34
 */
@Data
public class CacheParamDTO {
    /**
     * 返回数据类型
     */
    private Class<?> returnType;
    /**
     * 数据类型
     */
    private DataType dataType;
    /**
     * 缓存前缀
     */
    private String prefix;
    /**
     * key
     */
    private String key;
    /**
     * hashKey
     */
    private HashKey hashKey;
    /**
     * lruKey
     */
    private String lruKey;
    /**
     * lockKey
     */
    private String lockKey;
    /**
     * 查询缓存参数
     */
    private CacheQueryMeta queryMeta;
    /**
     * 更新缓存参数
     */
    private CacheUpdateMeta updateMeta;
    /**
     * 删除缓存参数
     */
    private CacheDeleteMeta deleteMeta;


    /**
     * 获取毫秒格式过期时间
     * @return  毫秒格式过期时间
     */
    public long getTimeOutMillis() {
        long timeOutMillis = getQueryMeta().getTimeUnit().toMillis(getQueryMeta().getTimeOut());
        if (getQueryMeta().getPreCacheSnowSlide()) {
            timeOutMillis = CacheUtil.getRandomTime(timeOutMillis);
        }
        return timeOutMillis;
    }
}
