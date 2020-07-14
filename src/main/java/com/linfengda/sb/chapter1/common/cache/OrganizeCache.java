package com.linfengda.sb.chapter1.common.cache;

import com.linfengda.sb.chapter1.common.cache.manager.CacheManager;
import com.linfengda.sb.support.cache.redis.SimpleRedisTemplate;
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
public class OrganizeCache implements Cache {
    @Resource
    private SimpleRedisTemplate jacksonRedisTemplate;

    @Override
    public void initCache() {
        log.warn("初始化组织关系缓存...");
    }

    @Override
    public void clearCache() {
        jacksonRedisTemplate.deleteObject(CacheManager.ORGANIZE_CACHE.getPrefix());
        log.warn("清除组织关系缓存...");
    }
}
