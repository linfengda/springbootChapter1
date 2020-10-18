package com.linfengda.sb.support.redis.cache.entity.meta;

import com.linfengda.sb.support.redis.cache.entity.type.CacheMaxSizeStrategy;
import lombok.Data;

/**
 * @description:
 * @author: linfengda
 * @date: 2020-07-22 18:40
 */
@Data
public class CacheQueryMeta extends CacheUpdateMeta {
    /**
     * 最大缓存数量
     */
    private Long maxSize;
    /**
     * 缓存最大数量淘汰策略
     */
    private CacheMaxSizeStrategy maxSizeStrategy;
    /**
     * 当lru缓存大小超出限制时，删除的key数量
     */
    private int deleteLruBatchNum;
    /**
     * 防止缓存击穿
     * @return
     */
    private Boolean preCacheHotKeyMultiLoad;
    /**
     * 等待缓存加载自旋时间
     */
    private Long spinTime;
    /**
     * 等待缓存加载自旋次数
     */
    private Integer maxSpinCount;
}
