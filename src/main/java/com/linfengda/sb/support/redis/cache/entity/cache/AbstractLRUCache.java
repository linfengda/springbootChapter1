package com.linfengda.sb.support.redis.cache.entity.cache;

import com.linfengda.sb.support.redis.cache.entity.dto.LruCacheDTO;
import com.linfengda.sb.support.redis.cache.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 描述: LRU二级缓存
 *
 * @author: linfengda
 * @date: 2020-07-15 10:49
 */
@Slf4j
public abstract class AbstractLRUCache {
    /**
     * 缓存最大数量
     */
    private Long maxSize;
    /**
     * 二级缓存
     */
    private ConcurrentHashMap<String, LruCacheDTO> lruCache = new ConcurrentHashMap<>(512);
    /**
     * 二级缓存LRU记录
     */
    private ConcurrentLinkedQueue<String> lruRecord = new ConcurrentLinkedQueue<>();

    public AbstractLRUCache(Long maxSize, Long cacheTimeout) {
        if (null == maxSize || 0 == maxSize) {
            throw new BusinessException("缓存最大数量必须大于0！");
        }
        if (null == cacheTimeout || 0 == cacheTimeout) {
            throw new BusinessException("缓存失效时间必须大于0！");
        }
        this.maxSize = maxSize;
    }

    /**
     * 清除缓存
     */
    public void release() {
        lruCache.clear();
        lruRecord.clear();
    }

    /**
     * 向缓存增加元素
     * @param value 元素，允许NULL值
     */
    protected void put(String key, LruCacheDTO value) {
        if (StringUtils.isEmpty(key) || null == value) {
            return;
        }
        if (lruCache.contains(key)) {
            lruCache.put(key, value);
            updateLruRecord(key);
            return;
        }
        while (maxSize <= lruCache.size()) {
            deleteLRU();
        }
        lruCache.put(key, value);
        updateLruRecord(key);
    }

    /**
     * LRU淘汰
     */
    protected void deleteLRU() {
        String delKey = lruRecord.poll();
        if (StringUtils.isEmpty(delKey)) {
            return;
        }
        lruCache.remove(delKey);
    }

    /**
     * 从缓存获取元素
     * @param key   key
     * @return      如果元素不存在则返回NULL
     */
    protected LruCacheDTO get(String key) {
        LruCacheDTO value = lruCache.get(key);
        if (null == value) {
            return null;
        }
        value.setLastAccessTime(System.currentTimeMillis());
        updateLruRecord(key);
        return value;
    }

    private synchronized void updateLruRecord(String key) {
        if (lruRecord.contains(key)) {
            lruRecord.remove(key);
            lruRecord.offer(key);
        }
    }
}
