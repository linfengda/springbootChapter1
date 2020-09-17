package com.linfengda.sb.support.redis.cache.resolver;

import com.alibaba.fastjson.JSON;
import com.linfengda.sb.support.redis.Constant;
import com.linfengda.sb.support.redis.GenericRedisTemplate;
import com.linfengda.sb.support.redis.cache.entity.bo.CacheResultBO;
import com.linfengda.sb.support.redis.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.redis.cache.entity.type.CacheMaxSizeStrategy;
import com.linfengda.sb.support.redis.cache.entity.type.CacheSizeStrategy;
import com.linfengda.sb.support.redis.util.CacheUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;

import java.util.Set;

/**
 * 描述:
 *
 * @author: linfengda
 * @date: 2020-07-15 17:54
 */
@Slf4j
public abstract class AbstractCacheDataTypeResolver implements CacheDataTypeResolver {
    @Setter
    protected GenericRedisTemplate genericRedisTemplate;


    @Override
    public CacheResultBO getCache(CacheParamDTO param) {
        CacheResultBO value = doGetCache(param);
        if (null == value) {
            return null;
        }
        if (CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_LRU == param.getQueryMeta().getMaxSizeStrategy()) {
            genericRedisTemplate.opsForZSet().add(param.getLruKey(), param.getKey(), CacheUtil.getKeyLruScore());
        }
        return value;
    }

    @Override
    public void setCache(CacheParamDTO param, Object value) {
        CacheSizeStrategy cacheSizeStrategy = checkCacheSize(param);
        if (CacheSizeStrategy.OVER_SIZE == cacheSizeStrategy) {
            log.debug("当前缓存大小超过限制：{}，将不再缓存数据！", param.getQueryMeta().getMaxSize());
            return;
        }
        doSetCache(param, value);
        if (CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_LRU == param.getQueryMeta().getMaxSizeStrategy()) {
            genericRedisTemplate.opsForZSet().add(param.getLruKey(), param.getKey(), CacheUtil.getKeyLruScore());
        }
    }

    protected void delAllEntries(CacheParamDTO param) {
        genericRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
            Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(param.getPrefix() + Constant.ASTERISK).count(Constant.DEFAULT_DELETE_CACHE_BATCH_NUM).build());
            while(cursor.hasNext()) {
                String key = new String(cursor.next());
                genericRedisTemplate.delete(key);
            }
            return true;
        });
    }

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
        if (CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_LRU == param.getQueryMeta().getMaxSizeStrategy()) {
            deleteLRU(param);
            return CacheSizeStrategy.NORMAL_SIZE;
        }else if (CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_ABANDON == param.getQueryMeta().getMaxSizeStrategy()) {
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
    protected CacheSizeStrategy getCacheSizeStrategy(CacheParamDTO param) {
        Long maxSize = param.getQueryMeta().getMaxSize();
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
    protected void deleteLRU(CacheParamDTO param) {
        Long size = genericRedisTemplate.opsForZSet().size(param.getLruKey());
        if (null == size || 0 == size) {
            return;
        }
        Set<Object> delKeys = genericRedisTemplate.opsForZSet().range(param.getLruKey(), 0, Constant.DEFAULT_LRU_CACHE_BG_REMOVE_BATCH_NUM-1);
        // 删除LRU记录
        genericRedisTemplate.opsForZSet().removeRange(param.getLruKey(), 0, Constant.DEFAULT_LRU_CACHE_BG_REMOVE_BATCH_NUM-1);
        // 删除具体缓存key
        for (Object delKey : delKeys) {
            if (null == delKey) {
                continue;
            }
            genericRedisTemplate.delete((String) delKey);
        }
        log.debug("当前缓存大小超过限制：{}，采取LRU算法淘汰{}条数据，lurKey={}，delKeys={}", param.getQueryMeta().getMaxSize(), Constant.DEFAULT_LRU_CACHE_BG_REMOVE_BATCH_NUM, param.getLruKey(), JSON.toJSONString(delKeys));
    }

    /**
     * 查询缓存
     * @param param 缓存参数
     * @return      缓存数据
     */
    public abstract CacheResultBO doGetCache(CacheParamDTO param);

    /**
     * 写入缓存
     * @param param 缓存参数
     * @param value 缓存数据
     */
    public abstract void doSetCache(CacheParamDTO param, Object value);
}
