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
     * 默认lru缓存后台自动清除批量：10个
     */
    int DEFAULT_LRU_CACHE_BG_REMOVE_BATCH_NUM = 10;
    /**
     * 默认渐进式删除缓存批量：100
     */
    int DEFAULT_DELETE_CACHE_BATCH_NUM = 100;
    /**
     * 默认防止缓存雪崩随机时间范围：60min
     */
    long DEFAULT_PRV_CACHE_SNOW_SLIDE_RANDOM_TIME = 60*60*1000;
    /**
     * 默认等待缓存加载自旋时间
     */
    long DEFAULT_LOAD_CACHE_SPIN_TIME = 50L;
    /**
     * 默认等待缓存加载自旋次数
     */
    int DEFAULT_LOAD_CACHE_SPIN_COUNT = 3;
}
