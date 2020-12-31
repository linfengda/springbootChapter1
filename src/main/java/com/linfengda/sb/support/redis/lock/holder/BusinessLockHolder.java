package com.linfengda.sb.support.redis.lock.holder;

import com.linfengda.sb.support.redis.lock.RedisDistributedLock;

/**
 * @description:
 * @author: linfengda
 * @date: 2020-12-29 23:18
 */
public enum  BusinessLockHolder {
    /**
     * 单例
     */
    INSTANCE;
    private static RedisDistributedLock lock;

    public static void init(RedisDistributedLock redisDistributedLock) {
        lock = redisDistributedLock;
    }

    public static RedisDistributedLock get() {
        return lock;
    }
}
