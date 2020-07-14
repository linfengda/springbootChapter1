package com.linfengda.sb.support.cache.redis;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 描述: redisTemplate
 *
 * @author linfengda
 * @create 2018-09-12 13:40
 */
public class SimpleRedisTemplate extends RedisTemplate<String, Object> {

    public void setObject(String key, Object value) {
        super.opsForValue().set(key, value);
    }

    public void setObject(String key, Object value, Long timeOut, TimeUnit timeUnit) {
        super.opsForValue().set(key, value, timeOut, timeUnit);
    }

    public <T> T getObject(String key) {
        return (T) super.opsForValue().get(key);
    }

    public Boolean deleteObject(String key) {
        return super.delete(key);
    }

    public void hashPut(String key, String hashKey, Object value) {
        super.opsForHash().put(key, hashKey, value);
    }

    public void hashPut(String key, String hashKey, Object value, Long timeOut, TimeUnit timeUnit) {
        super.opsForHash().put(key, hashKey, value);
        super.expire(key, timeOut, timeUnit);
    }

    public void hashPutAll(String key, Map<String, Object> values) {
        super.opsForHash().putAll(key, values);
    }

    public void hashPutAll(String key, Map<String, Object> values, Long timeOut, TimeUnit timeUnit) {
        super.opsForHash().putAll(key, values);
        super.expire(key, timeOut, timeUnit);
    }

    public <T> T hashGet(String key, String hashKey) {
        return (T) super.opsForHash().get(key, hashKey);
    }

    public <T> Set<T> hashKeys(String key) {
        return (Set<T>) super.opsForHash().keys(key);
    }

    public <T> List<T> hashValues(String key) {
        return (List<T>) super.opsForHash().values(key);
    }

    public Long hashDel(String key, String... hashKeys) {
        return super.opsForHash().delete(key, hashKeys);
    }

    public Boolean hasHashKey(String key, String hashKey) {
        return super.opsForHash().hasKey(key, hashKey);
    }

    public void listAdd(String key, Object value) {
        super.opsForList().rightPushAll(key, value);
    }

    public void listAdd(String key, Object value, Long timeOut, TimeUnit timeUnit) {
        super.opsForList().rightPushAll(key, value);
        super.expire(key, timeOut, timeUnit);
    }

    public void listAddAll(String key, Collection values) {
        super.opsForList().rightPushAll(key, values);
    }

    public void listAddAll(String key, Collection values, Long timeOut, TimeUnit timeUnit) {
        super.opsForList().rightPushAll(key, values);
        super.expire(key, timeOut, timeUnit);
    }

    public <T> List<T> listGet(String key) {
        List list = super.opsForList().range(key, 0, -1);
        return null == list ? null : (List<T>) list;
    }

    public void setAdd(String key, Object value) {
        super.opsForSet().add(key, value);
    }

    public void setAdd(String key, Object value, Long timeOut, TimeUnit timeUnit) {
        super.opsForSet().add(key, value);
        super.expire(key, timeOut, timeUnit);
    }

    public void setAddAll(String key, Collection values) {
        for (Object value : values) {
            super.opsForSet().add(key, value);
        }
    }

    public void setAddAll(String key, Collection values, Long timeOut, TimeUnit timeUnit) {
        for (Object value : values) {
            super.opsForSet().add(key, value);
        }
        super.expire(key, timeOut, timeUnit);
    }

    public <T> Set<T> setGet(String key) {
        Set set = super.opsForSet().members(key);
        return null == set ? null : (Set<T>) set;
    }

    public Long setDelete(String key, Object... values) {
        return super.opsForSet().remove(key, values);
    }
}
