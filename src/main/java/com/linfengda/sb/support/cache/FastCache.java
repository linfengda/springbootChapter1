package com.linfengda.sb.support.cache;

import com.linfengda.sb.support.middleware.redis.SimpleRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 描述: 快速缓存
 *
 * @author linfengda
 * @create 2019-08-15 11:33
 */
@Slf4j
@Component
public class FastCache {
    private SimpleRedisTemplate simpleRedisTemplate;

    /**
     * 缓存对象
     * @param key
     * @param value
     */
    public void cache(String key, Object value, long timeOut, TimeUnit timeUnit) {
        simpleRedisTemplate.setObject(key, value);
        simpleRedisTemplate.expire(key, timeOut, timeUnit);
    }

    /**
     * 获取缓存对象
     * @param key
     * @param clz
     * @return
     */
    public Object getCache(String key, Class<T> clz) {
        return simpleRedisTemplate.getObject(key, clz);
    }
}
