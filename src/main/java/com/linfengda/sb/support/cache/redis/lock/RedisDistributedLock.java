package com.linfengda.sb.support.cache.redis.lock;

import com.linfengda.sb.chapter1.common.util.ServerRunTimeUtil;
import com.linfengda.sb.support.cache.redis.SimpleRedisTemplate;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 描述: Redis分布式锁
 *
 * @author linfengda
 * @create 2020-03-23 17:33
 */
@Slf4j
public class RedisDistributedLock {
    /**
     * 锁默认超时时间
     */
    private final int DEFAULT_LOCK_EXPIRE_TIME = 60;
    /**
     * 阻塞锁最大阻塞时间
     */
    private final long DEFAULT_LOCK_WAIT_TIME = 30 * 1000L;

    @Setter
    private SimpleRedisTemplate simpleRedisTemplate;

    /**
     * 加锁操作：多个
     * @param keys
     * @return
     */
    public Boolean tryLock(String[] keys) {
        String currentLockRequester = getCurrentLockRequester();
        Map map = new HashMap(16);
        for (String key : keys) {
            map.put(key, currentLockRequester);
        }
        Boolean result = simpleRedisTemplate.opsForValue().multiSetIfAbsent(map);
        if (result) {
            for (String key : keys) {
                simpleRedisTemplate.expire(key, DEFAULT_LOCK_EXPIRE_TIME, TimeUnit.SECONDS);
                log.info("key={}，currentLockRequester={}，lock success", key, currentLockRequester);
            }
        }
        return result;
    }

    /**
     * 加锁操作：单个
     * @param key
     * @return
     */
    public Boolean tryLock(String key) {
        String currentLockRequester = getCurrentLockRequester();
        Boolean result = simpleRedisTemplate.opsForValue().setIfAbsent(key, currentLockRequester);
        if (result) {
            simpleRedisTemplate.expire(key, DEFAULT_LOCK_EXPIRE_TIME, TimeUnit.SECONDS);
            log.info("key={}，currentLockRequester={}，lock success", key, currentLockRequester);
        }
        return result;
    }

    /**
     * 阻塞锁：单个
     * @param key
     * @return
     */
    public Boolean lock(String key) {
        long startTime = System.currentTimeMillis();
        while (!tryLock(key)) {
            try {
                if (DEFAULT_LOCK_WAIT_TIME < System.currentTimeMillis() - startTime) {
                    return false;
                }
                Thread.sleep(50);
            } catch (InterruptedException e) {
                log.error("获取redis阻塞锁失败！", e);
                return false;
            }
        }
        return true;
    }

    /**
     * 解锁操作：多个
     * @param keys
     * @return
     */
    public Boolean unLock(String[] keys) {
        for (String key : keys) {
            unLock(key);
        }
        return true;
    }

    /**
     * 解锁操作：单个
     * @param key
     * @return
     */
    public Boolean unLock(String key) {
        Object lockRequester = simpleRedisTemplate.getObject(key);
        String currentLockRequester = getCurrentLockRequester();
        if (null == lockRequester) {
            return true;
        }
        if (lockRequester.equals(currentLockRequester)) {
            simpleRedisTemplate.delete(key);
            log.info("key={}，currentLockRequester={}，unlock success", key, currentLockRequester);
            return true;
        }
        return false;
    }

    /**
     * 获取当前加锁者
     * @return jvmName+ip+threadName+threadID
     */
    private String getCurrentLockRequester() {
        StringBuilder builder = new StringBuilder();
        builder.append(ServerRunTimeUtil.getJvmName());
        builder.append(ServerRunTimeUtil.getIp());
        builder.append(Thread.currentThread().getName());
        builder.append(Thread.currentThread().getId());
        return builder.toString();
    }
}
