package com.linfengda.sb.support.cache.handler.strategy;

import com.alibaba.fastjson.JSON;
import com.linfengda.sb.support.cache.config.Constant;
import com.linfengda.sb.support.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.cache.entity.type.CacheExtraStrategy;
import com.linfengda.sb.support.cache.entity.type.CacheMaxSizeStrategy;
import com.linfengda.sb.support.cache.entity.type.CacheSizeStrategy;
import com.linfengda.sb.support.cache.redis.template.SimpleRedisTemplate;
import com.linfengda.sb.support.cache.util.CacheUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * 描述:
 *
 * @author: linfengda
 * @date: 2020-07-15 17:54
 */
@Slf4j
@Data
public abstract class AbstractCacheStrategy implements CacheStrategy {
    private SimpleRedisTemplate simpleRedisTemplate;


    /**
     * 检查缓存是否超过最大数量
     * @param param 查询参数
     * @return      缓存最大数量类型
     */
    protected CacheSizeStrategy checkCacheSize(CacheParamDTO param) {
        CacheSizeStrategy cacheSizeStrategy = getCacheSizeStrategy(param);
        if (CacheSizeStrategy.OVER_SIZE != cacheSizeStrategy) {
            return cacheSizeStrategy;
        }
        if (CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_LRU == param.getMaxSizeStrategy()) {
            deleteLRU(param);
            return CacheSizeStrategy.NORMAL_SIZE;
        }else if (CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_ABANDON == param.getMaxSizeStrategy()) {
            return CacheSizeStrategy.OVER_SIZE;
        }else {
            return CacheSizeStrategy.OVER_SIZE;
        }
    }

    /**
     * 获取当前缓存最大数量策略类型
     * @param param 缓存参数
     * @return      缓存最大数量策略类型 {@link CacheSizeStrategy}
     */
    private CacheSizeStrategy getCacheSizeStrategy(CacheParamDTO param) {
        Long maxSize = param.getMaxSize();
        if (Constant.DEFAULT_NO_SIZE_LIMIT.equals(maxSize)) {
            return CacheSizeStrategy.UN_LIMIT;
        }
        if (maxSize > getCurrentCacheSize(param)) {
            return CacheSizeStrategy.NORMAL_SIZE;
        }
        return CacheSizeStrategy.OVER_SIZE;
    }

    /**
     * 采取LRU算法淘汰数据
     * @param param 查询参数
     */
    private void deleteLRU(CacheParamDTO param) {
        Long size = getSimpleRedisTemplate().opsForZSet().size(param.getLruKey());
        if (null == size || 0 == size) {
            return;
        }
        Set<Object> delKeys = getSimpleRedisTemplate().opsForZSet().range(param.getLruKey(), 0, Constant.DEFAULT_LRU_REMOVE_NUM-1);
        // 删除LRU记录
        getSimpleRedisTemplate().opsForZSet().removeRange(param.getLruKey(), 0, Constant.DEFAULT_LRU_REMOVE_NUM-1);
        // 删除具体缓存key
        for (Object delKey : delKeys) {
            if (null == delKey) {
                continue;
            }
            getSimpleRedisTemplate().deleteObject((String) delKey);
        }
        log.debug("当前缓存大小超过限制：{}，采取LRU算法淘汰{}条数据，lurKey={}，delKeys={}", param.getMaxSize(), Constant.DEFAULT_LRU_REMOVE_NUM, param.getLruKey(), JSON.toJSONString(delKeys));
    }
}
