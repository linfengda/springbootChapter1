package com.linfengda.sb.chapter1.common.cache;

import com.linfengda.sb.support.middleware.redis.SimpleRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 描述: 组织关系缓存
 *
 * @author linfengda
 * @create 2020-01-14 13:44
 */
@Slf4j
@Component
public class OrganizeCache implements AutoClearCache {
    @Resource
    private SimpleRedisTemplate simpleRedisTemplate;

    @Override
    public void clearCache() {
        simpleRedisTemplate.deleteObject(CacheManager.ORGANIZE_CACHE.getPrefix());
        log.warn("清除组织关系缓存...");
    }
}
