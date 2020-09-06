package com.linfengda.sb.support.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 封装通用的redisTemplate
 *
 * @author linfengda
 * @create 2018-09-12 13:40
 */
@Slf4j
public class GenericRedisTemplate extends RedisTemplate<String, Object> {

    public void setObject(String key, Object value) {
        super.opsForValue().set(key, value);
    }

    public void setObject(String key, Object value, Long timeOut, TimeUnit timeUnit) {
        if (Constant.DEFAULT_NO_EXPIRE_TIME.equals(timeOut)) {
            setObject(key, value);
            return;
        }
        super.opsForValue().set(key, value, timeOut, timeUnit);
    }

    public <T> T getObject(String key) {
        return (T) super.opsForValue().get(key);
    }

    public void hashPut(String key, String hashKey, Object value) {
        super.opsForHash().put(key, hashKey, value);
    }

    public void hashPut(String key, String hashKey, Object value, Long timeOut, TimeUnit timeUnit) {
        if (Constant.DEFAULT_NO_EXPIRE_TIME.equals(timeOut)) {
            hashPut(key, hashKey, value);
            return;
        }
        super.opsForHash().put(key, hashKey, value);
        super.expire(key, timeOut, timeUnit);
    }

    public void hashPutAll(String key, Map<String, Object> values) {
        super.opsForHash().putAll(key, values);
    }

    public void hashPutAll(String key, Map<String, Object> values, Long timeOut, TimeUnit timeUnit) {
        if (Constant.DEFAULT_NO_EXPIRE_TIME.equals(timeOut)) {
            hashPutAll(key, values);
            return;
        }
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

    public Boolean hasHashKey(String key, String hashKey) {
        return super.opsForHash().hasKey(key, hashKey);
    }

    public Long hashDel(String key, String... hashKeys) {
        return super.opsForHash().delete(key, hashKeys);
    }

    public void listAdd(String key, Object value) {
        super.opsForList().rightPushAll(key, value);
    }

    public void listAdd(String key, Object value, Long timeOut, TimeUnit timeUnit) {
        if (Constant.DEFAULT_NO_EXPIRE_TIME.equals(timeOut)) {
            listAdd(key, value);
            return;
        }
        super.opsForList().rightPushAll(key, value);
        super.expire(key, timeOut, timeUnit);
    }

    public void listAddAll(String key, Collection values) {
        super.opsForList().rightPushAll(key, values);
    }

    public void listAddAll(String key, Collection values, Long timeOut, TimeUnit timeUnit) {
        if (Constant.DEFAULT_NO_EXPIRE_TIME.equals(timeOut)) {
            listAddAll(key, values);
            return;
        }
        super.opsForList().rightPushAll(key, values);
        super.expire(key, timeOut, timeUnit);
    }

    public <T> T listGet(String key) {
        return (T) super.opsForList().leftPop(key);
    }

    public <T> List<T> listGetAll(String key) {
        return (List<T>) super.opsForList().range(key, 0, -1);
    }

    public void setAdd(String key, Object value) {
        super.opsForSet().add(key, value);
    }

    public void setAdd(String key, Object value, Long timeOut, TimeUnit timeUnit) {
        if (Constant.DEFAULT_NO_EXPIRE_TIME.equals(timeOut)) {
            setAdd(key, value);
            return;
        }
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
        if (!Constant.DEFAULT_NO_EXPIRE_TIME.equals(timeOut)) {
            super.expire(key, timeOut, timeUnit);
        }
    }

    public <T> T setGet(String key) {
        return (T) super.opsForSet().pop(key);
    }

    public <T> Set<T> setGetAll(String key) {
        return  (Set<T>) super.opsForSet().members(key);
    }

    public Long setDelete(String key, Object... values) {
        return super.opsForSet().remove(key, values);
    }
}
