package com.linfengda.sb.chapter1.cache.impl;

import com.linfengda.sb.chapter1.cache.SystemCachePrefix;
import com.linfengda.sb.support.redis.GenericRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 描述: 用户token-userId缓存
 *
 * @author linfengda
 * @create 2020-01-14 13:44
 */
@Slf4j
@Component
public class UserTokenCache {
    @Resource
    private GenericRedisTemplate genericRedisTemplate;

    public void initCache() {
        log.warn("初始化用户token缓存...");
    }

    public void clearCache() {
        genericRedisTemplate.delete(SystemCachePrefix.USER_TOKEN_CACHE);
        log.info("清除用户token缓存...");
    }

    public void put(String userId, String token){
        genericRedisTemplate.hashPut(SystemCachePrefix.USER_TOKEN_CACHE, userId, token);
    }

    public void remove(String userId){
        genericRedisTemplate.hashDel(SystemCachePrefix.USER_TOKEN_CACHE, userId);
    }

    public String getToken(String userId){
        return genericRedisTemplate.hashGet(SystemCachePrefix.USER_TOKEN_CACHE, userId);
    }
}
