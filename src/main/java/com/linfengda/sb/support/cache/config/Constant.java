package com.linfengda.sb.support.cache.config;

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
    String ASTERISK = ":";
    /**
     * 默认缓存过期时间
     */
    Long DEFAULT_NO_EXPIRE_TIME = -1L;
    /**
     * 默认最大缓存数量
     */
    Long DEFAULT_NO_SIZE_LIMIT = -1L;
    /**
     * 防止缓存雪崩随机时间范围：1s
     */
    Integer DEFAULT_NO_CACHE_SNOW_SLIDE_RANDOM_MS = 60*1000;
}
