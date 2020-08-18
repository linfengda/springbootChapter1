package com.linfengda.sb.support.redis.cache.entity;

import com.linfengda.sb.support.redis.JacksonRedisTemplate;
import com.linfengda.sb.support.redis.RedisDistributedLock;
import lombok.Data;

/**
 * @description: redis操作支持
 * @author: linfengda
 * @date: 2020-08-16 15:44
 */
@Data
public class RedisSupport {
    private JacksonRedisTemplate jacksonRedisTemplate;
    private RedisDistributedLock redisDistributedLock;
}
