package com.linfengda.sb.chapter1.system.cache.impl;

import com.linfengda.sb.chapter1.system.cache.CacheManager;
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
        genericRedisTemplate.delete(CacheManager.USER_TOKEN_CACHE.getPrefix());
        log.warn("清除用户token缓存...");
    }

    public void put(String userId, String token){
        genericRedisTemplate.hashPut(CacheManager.USER_TOKEN_CACHE.getPrefix(), userId, token);
    }

    public void remove(String userId){
        genericRedisTemplate.hashDel(CacheManager.USER_TOKEN_CACHE.getPrefix(), userId);
    }

    public String getToken(String userId){
        return genericRedisTemplate.hashGet(CacheManager.USER_TOKEN_CACHE.getPrefix(), userId);
    }
}
