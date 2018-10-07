package com.linfengda.sb.support.middleware.redis;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 描述: 统一的数据结构的操作行为，使模板与序列化方式解耦。
 *
 * @author linfengda
 * @create 2018-10-03 20:04
 */
public abstract class SimpleRedisTemplate extends RedisTemplate<String, Object> {

    public abstract void setObject(String key, Object value);

    public abstract <T> T getObject(String key, Class<T> clazz);

    public abstract Boolean deleteObject(String key);

    public abstract void listAdd(String key, Object... values);

    public abstract void listAddAll(String key, Collection values);

    public abstract <T> List<T> listGet(String key, Class<T> clazz);

    public abstract void setAdd(String key, Object... values);

    public abstract void setAddAll(String key, Collection values);

    public abstract <T> Set<T> setGet(String key, Class<T> clazz);

    public abstract Long setDelete(String key, Object... values);

    public abstract void mapPut(String key, String hashKey, Object value);

    public abstract <T> T mapGet(String key, String hashKey, Class<T> clazz);

    public abstract Long mapDelete(String key, String... hashKeys);

}
