package com.linfengda.sb.support.middleware.redis.template;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 描述: 使用Jackson2JsonRedisSerializer序列化
 *
 * @author linfengda
 * @create 2018-09-12 13:40
 */
public class JacksonRedisTemplate extends RedisTemplate<String, Object> {

    public void setObject(String key, Object value) {
        super.opsForValue().set(key, value);
    }

    public <T> T getObject(String key, Class<T> clazz) {
        T object = (T) super.opsForValue().get(key);
        return object;
    }

    public Boolean deleteObject(String key) {
        return super.delete(key);
    }

    public void listAdd(String key, Object... values) {
        super.opsForList().rightPushAll(key, values);
    }

    public void listAddAll(String key, Collection values) {
        for (Object value : values) {
            super.opsForList().rightPushAll(key, value);
        }
    }

    public <T> List<T> listGet(String key, Class<T> clazz) {
        List list = super.opsForList().range(key, 0, -1);
        return null == list ? null : (List<T>) list;
    }

    public void setAdd(String key, Object... values) {
        super.opsForSet().add(key, values);
    }

    public void setAddAll(String key, Collection values) {
        for (Object value : values) {
            super.opsForSet().add(key, value);
        }
    }

    public <T> Set<T> setGet(String key, Class<T> clazz) {
        Set set =  super.opsForSet().members(key);
        return null == set ? null : (Set<T>) set;
    }

    public Long setDelete(String key, Object... values) {
        return super.opsForSet().remove(key, values);
    }

    public void mapPut(String key, String hashKey, Object value) {
        super.opsForHash().put(key, hashKey, value);
    }

    public <T> T mapGet(String key, String hashKey, Class<T> clazz) {
        return (T) super.opsForHash().get(key, hashKey);
    }

    public Long mapDelete(String key, String... hashKeys) {
        return super.opsForHash().delete(key, hashKeys);
    }
}
