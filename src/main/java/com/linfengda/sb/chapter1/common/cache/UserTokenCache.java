package com.linfengda.sb.chapter1.common.cache;

import com.linfengda.sb.chapter1.common.cache.manager.CacheManager;
import com.linfengda.sb.support.redis.JacksonRedisTemplate;
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
public class UserTokenCache implements Cache {
    @Resource
    private JacksonRedisTemplate jacksonRedisTemplate;

    @Override
    public void initCache() {
        log.warn("初始化token-userId缓存...");
    }

    @Override
    public void clearCache() {
        jacksonRedisTemplate.delete(CacheManager.USER_TOKEN_CACHE.getPrefix());
        log.warn("清除组织关系缓存...");
    }
}
