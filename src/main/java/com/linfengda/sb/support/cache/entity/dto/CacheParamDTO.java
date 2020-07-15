package com.linfengda.sb.support.cache.entity.dto;

import com.linfengda.sb.support.cache.builder.HashKey;
import com.linfengda.sb.support.cache.entity.type.CacheExtraStrategy;
import com.linfengda.sb.support.cache.entity.type.DataType;
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
     * 指定缓存策略
     */
    private List<CacheExtraStrategy> strategies;
    /**
     * 最大缓存数量
     */
    private Long maxSize;
    /**
     * 是否删除前缀的所有缓存
     * @return
     */
    private Boolean allEntries;
}
