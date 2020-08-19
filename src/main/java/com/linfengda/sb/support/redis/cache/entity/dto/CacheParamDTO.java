package com.linfengda.sb.support.redis.cache.entity.dto;

import com.linfengda.sb.support.redis.cache.builder.HashKey;
import com.linfengda.sb.support.redis.cache.entity.type.CacheExtraStrategy;
import com.linfengda.sb.support.redis.cache.entity.type.CacheMaxSizeStrategy;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import com.linfengda.sb.support.redis.util.CacheUtil;
import lombok.Data;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 缓存查询参数DTO
 *
 * @author: linfengda
 * @date: 2020-07-10 14:34
 */
@Data
public class CacheParamDTO {
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
     * lru key
     */
    private String lruKey;
    /**
     * lock key
     */
    private String lockKey;
    /**
     * 缓存失效时间
     */
    private Long timeOut;
    /**
     * 缓存失效时间单位
     */
    TimeUnit timeUnit;
    /**
     * 缓存策略
     */
    private List<CacheExtraStrategy> strategies;
    /**
     * 缓存最大数量淘汰策略
     */
    private CacheMaxSizeStrategy maxSizeStrategy;
    /**
     * 最大缓存数量
     */
    private Long maxSize;
    /**
     * 是否删除前缀的所有缓存
     * @return
     */
    private Boolean allEntries;

    /**
     * 获取毫秒格式过期时间
     * @return  毫秒格式过期时间
     */
    public long getTimeOutMillis() {
        long timeOutMillis = getTimeUnit().toMillis(getTimeOut());
        if (getStrategies().contains(CacheExtraStrategy.PRV_CACHE_SNOW_SLIDE)) {
            timeOutMillis = CacheUtil.getRandomTime(timeOutMillis);
        }
        return timeOutMillis;
    }
}
