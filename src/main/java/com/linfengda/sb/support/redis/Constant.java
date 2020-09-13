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
     * 默认lru缓存后台自动清除间隔：10min
     */
    long DEFAULT_LRU_CACHE_BG_REMOVE_INTERNAL = 10*60*1000L;
    /**
     * 默认后台批量处理LRU缓存数量
     */
    Integer DEFAULT_BG_REMOVE_LRU_BATCH_NUM = 10;
    /**
     * 默认LRU一次性淘汰数量：100
     */
    Integer DEFAULT_LRU_REMOVE_NUM = 100;
    /**
     * 默认删除缓存批量数量：1000
     */
    Integer DEFAULT_DELETE_CACHE_LIMIT = 1000;
    /**
     * 默认防止缓存雪崩随机时间范围：60s
     */
    Integer DEFAULT_NO_CACHE_SNOW_SLIDE_RANDOM_MS = 60*1000;
}
