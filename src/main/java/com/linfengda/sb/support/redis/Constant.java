package com.linfengda.sb.support.redis;

/**
 * @description: 项目常量定义
 * @author: linfengda
 * @date: 2020-05-25 09:20
 */
public interface Constant {
    /**
     * 冒号
     */
    String COLON = ":";
    /**
     * 星号
     */
    String ASTERISK = "*";
    /**
     * LRU记录前缀
     */
    String LRU_RECORD_PREFIX = "lru:rd";
    /**
     * LOCK前缀
     */
    String LOCK_PREFIX = "lk";
    /**
     * 默认不设置缓存过期时间
     */
    Long DEFAULT_NO_EXPIRE_TIME = -1L;
    /**
     * 默认不设置最大缓存数量
     */
    Long DEFAULT_NO_SIZE_LIMIT = -1L;
    /**
     * 默认后台渐进淘汰lru key数量
     */
    Integer DEFAULT_BG_LRU_REMOVE_BATCH_NUM = 10;
    /**
     * 默认LRU一次性淘汰数量
     */
    Integer DEFAULT_LRU_REMOVE_NUM = 10;
    /**
     * 防止缓存雪崩随机时间范围：60s
     */
    Integer DEFAULT_NO_CACHE_SNOW_SLIDE_RANDOM_MS = 60*1000;
    /**
     * 清除LRU缓存定时任务间隔时间：10min
     */
    Integer DEFAULT_LRU_CACHE_CLEAR_TASK = 10*60*1000;
}
