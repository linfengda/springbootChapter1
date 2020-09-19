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
    String DEFAULT_LRU_RECORD_PREFIX = "lru";
    /**
     * 分布式锁前缀
     */
    String DEFAULT_LOCK_PREFIX = "lk";
    /**
     * 后台lru key清除分布式锁前缀
     */
    String DEFAULT_BG_LRU_LOCK_PREFIX = "bg:lru:lk";
    /**
     * 默认不设置缓存过期时间
     */
    long DEFAULT_NO_EXPIRE_TIME = 0;
    /**
     * 默认不设置最大缓存数量
     */
    long DEFAULT_NO_SIZE_LIMIT = 0;
    /**
     * 默认lru缓存后台自动清除间隔：10min
     */
    long DEFAULT_LRU_CACHE_BG_REMOVE_INTERNAL = 10*60*1000;
    /**
     * 默认lru缓存后台自动清除批量：10个
     */
    int DEFAULT_LRU_CACHE_BG_REMOVE_BATCH_NUM = 10;
    /**
     * 默认渐进式删除缓存批量：100
     */
    int DEFAULT_DELETE_CACHE_BATCH_NUM = 100;
}
