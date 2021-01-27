package com.linfengda.sb.support.redis.cache.entity.dto;

import com.linfengda.sb.support.redis.cache.entity.meta.CacheQueryMeta;
import com.linfengda.sb.support.redis.cache.entity.type.CacheAnnotationType;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import lombok.Data;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 缓存查询参数DTO
 *
 * @author linfengda
 * @date 2020-07-26 14:34
 */
@Data
public class CacheParamDTO extends CacheQueryMeta {
    /**
     * 返回数据类型
     */
    private Class<?> returnType;
    /**
     * 缓存操作类型
     */
    private CacheAnnotationType cacheAnnotationType;
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
     * 缓存失效时间
     */
    private Long timeOut;
    /**
     * 缓存失效时间单位
     */
    TimeUnit timeUnit;


    /**
     * 获取毫秒格式过期时间
     * @return  毫秒格式过期时间
     */
    public long getTimeOutMillis() {
        long timeOutMillis = getTimeUnit().toMillis(getTimeOut());
        if (getPreCacheSnowSlide()) {
            Random random = new Random();
            int randomTime = random.nextInt(getPreCacheSnowSlideTime().intValue());
            return timeOutMillis + randomTime;
        }
        return timeOutMillis;
    }

    /**
     * lruKeyScore=now+expireTime，确保排名的同时保证可以被后台清除线程识别
     * @return  lruKeyScore
     */
    public double getLruKeyScore() {
        long timeOutMillis = getTimeUnit().toMillis(getTimeOut());
        return (double) (System.currentTimeMillis() + timeOutMillis);
    }
}
