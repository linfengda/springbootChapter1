package com.linfengda.sb.support.middleware.redis;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 描述: 当使用Jackson2JsonRedisSerializer时，需要使用这个模板。
 *
 * @author linfengda
 * @create 2018-09-12 13:40
 */
public class SimpleRedisTemplate4JS extends SimpleRedisTemplate {

    @Override
    public void setObject(String key, Object value) {
        super.opsForValue().set(key, value);
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        T object = (T) super.opsForValue().get(key);
        return object;
    }

    @Override
    public Boolean deleteObject(String key) {
        return super.delete(key);
    }

    @Override
    public void listAdd(String key, Object... values) {
        super.opsForList().rightPushAll(key, values);
    }

    @Override
    public void listAddAll(String key, Collection values) {
        for (Object value : values) {
            super.opsForList().rightPushAll(key, value);
        }
    }

    @Override
    public <T> List<T> listGet(String key, Class<T> clazz) {
        List list = super.opsForList().range(key, 0, -1);
        return null == list ? null : (List<T>) list;
    }

    @Override
    public void setAdd(String key, Object... values) {
        super.opsForSet().add(key, values);
    }

    @Override
    public void setAddAll(String key, Collection values) {
        for (Object value : values) {
            super.opsForSet().add(key, value);
        }
    }

    @Override
    public <T> Set<T> setGet(String key, Class<T> clazz) {
        Set set =  super.opsForSet().members(key);
        return null == set ? null : (Set<T>) set;
    }

    @Override
    public Long setDelete(String key, Object... values) {
        return super.opsForSet().remove(key, values);
    }

    @Override
    public void mapPut(String key, String hashKey, Object value) {
        super.opsForHash().put(key, hashKey, value);
    }

    @Override
    public <T> T mapGet(String key, String hashKey, Class<T> clazz) {
        return (T) super.opsForHash().get(key, hashKey);
    }

    @Override
    public Long mapDelete(String key, String... hashKeys) {
        return super.opsForHash().delete(key, hashKeys);
    }
}
