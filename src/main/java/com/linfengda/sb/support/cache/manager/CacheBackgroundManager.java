package com.linfengda.sb.support.cache.manager;

import com.alibaba.fastjson.JSON;
import com.linfengda.sb.support.cache.config.Constant;
import com.linfengda.sb.support.cache.config.RedisSupportHolder;
import com.linfengda.sb.support.cache.redis.template.SimpleRedisTemplate;
import com.linfengda.sb.support.cache.util.CacheUtil;
import com.linfengda.sb.support.cache.util.ThreadPoolHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * 描述: 缓存后台管理
 *
 * @author: linfengda
 * @date: 2020-07-21 14:57
 */
@Slf4j
@SpringBootConfiguration
public class CacheBackgroundManager {
    private static final ThreadPoolTaskExecutor CacheBgThreadPool = ThreadPoolHelper.initThreadPool(1, 10, "cache-bg-thread");

    @PostConstruct
    public void init() {
        CacheBgThreadPool.execute(() -> {
            while(true) {
                try {
                    Thread.sleep(Constant.DEFAULT_LRU_CACHE_CLEAR_TASK);
                    SimpleRedisTemplate simpleRedisTemplate = RedisSupportHolder.getSimpleRedisTemplate();
                    Set<String> lruKeys = simpleRedisTemplate.keys(Constant.LRU_RECORD_PREFIX + Constant.ASTERISK);
                    if (CollectionUtils.isEmpty(lruKeys)) {
                        continue;
                    }
                    for (String lruKey : lruKeys) {
                        Set<Object> expireKeys = simpleRedisTemplate.opsForZSet().rangeByScore(lruKey, 0, CacheUtil.getLruKeyScore());
                        if (CollectionUtils.isEmpty(expireKeys)) {
                            simpleRedisTemplate.delete(lruKey);
                            continue;
                        }
                        simpleRedisTemplate.opsForZSet().remove(lruKey, expireKeys.toArray());
                        log.info("清除LRU缓存记录，lruKey={}，expireKeys={}", lruKey, JSON.toJSON(expireKeys));
                    }
                }catch (Exception e) {
                    log.error("清除LRU缓存失败！");
                }
            }
        });
    }
}
