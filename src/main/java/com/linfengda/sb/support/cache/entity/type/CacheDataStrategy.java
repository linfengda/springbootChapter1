package com.linfengda.sb.support.cache.entity.type;

import com.linfengda.sb.support.cache.builder.CacheKeyBuilder;
import com.linfengda.sb.support.cache.builder.HashKey;
import com.linfengda.sb.support.cache.entity.meta.CacheMethodMeta;
import com.linfengda.sb.support.cache.manager.RedisTemplateHolder;
import com.linfengda.sb.support.middleware.redis.template.JacksonRedisTemplate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;

/**
 * 描述: 缓存数据类型操作策略
 *
 * @author: linfengda
 * @date: 2020-07-07 16:36
 */
@Getter
@AllArgsConstructor
public enum CacheDataStrategy {
    /**
     * object
     */
    OBJECT(DataType.OBJECT, "对象") {

        @Override
        public Object getCache(CacheMethodMeta cacheMethodMeta) {
            JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
            String key = CacheKeyBuilder.INSTANCE.buildObjectKey(cacheMethodMeta);
            Object value = jacksonRedisTemplate.getObject(key, cacheMethodMeta.getReturnType());
            return value;
        }

        @Override
        public void setCache(CacheMethodMeta cacheMethodMeta, Object value) {
            JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
            String key = CacheKeyBuilder.INSTANCE.buildObjectKey(cacheMethodMeta);
            jacksonRedisTemplate.setObject(key, value);
        }
    },
    /**
     * hash
     */
    HASH(DataType.HASH, "哈希"){

        @Override
        public Object getCache(CacheMethodMeta cacheMethodMeta) {
            JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
            HashKey hashKey = CacheKeyBuilder.INSTANCE.buildHashKey(cacheMethodMeta);
            Object value = jacksonRedisTemplate.mapGet(hashKey.getMapName(), hashKey.getHashKey(), cacheMethodMeta.getReturnType());
            return value;
        }

        @Override
        public void setCache(CacheMethodMeta cacheMethodMeta, Object value) {
            JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
            String key = CacheKeyBuilder.INSTANCE.buildObjectKey(cacheMethodMeta);
            HashKey hashKey = CacheKeyBuilder.INSTANCE.buildHashKey(cacheMethodMeta);
            jacksonRedisTemplate.mapPut(hashKey.getMapName(), hashKey.getHashKey(), value);
        }
    },
    /**
     * list
     */
    LIST(DataType.LIST, "列表"){

        @Override
        public Object getCache(CacheMethodMeta cacheMethodMeta) {
            JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
            String key = CacheKeyBuilder.INSTANCE.buildObjectKey(cacheMethodMeta);
            Object value = jacksonRedisTemplate.listGet(key, cacheMethodMeta.getReturnType());
            return value;
        }

        @Override
        public void setCache(CacheMethodMeta cacheMethodMeta, Object value) {
            JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
            String key = CacheKeyBuilder.INSTANCE.buildObjectKey(cacheMethodMeta);
            if (value instanceof List) {
                List list = (List) value;
                jacksonRedisTemplate.listAddAll(key, list);
            }else if (value instanceof Set) {
                Set set = (Set) value;
                jacksonRedisTemplate.listAddAll(key, set);
            }else {
                jacksonRedisTemplate.listAdd(key, value);
            }
        }
    },
    /**
     * set
     */
    SET(DataType.SET, "集合"){

        @Override
        public Object getCache(CacheMethodMeta cacheMethodMeta) {
            JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
            String key = CacheKeyBuilder.INSTANCE.buildObjectKey(cacheMethodMeta);
            Object value = jacksonRedisTemplate.setGet(key, cacheMethodMeta.getReturnType());
            return value;
        }

        @Override
        public void setCache(CacheMethodMeta cacheMethodMeta, Object value) {
            JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
            String key = CacheKeyBuilder.INSTANCE.buildObjectKey(cacheMethodMeta);
            if (value instanceof List) {
                List list = (List) value;
                jacksonRedisTemplate.setAddAll(key, list);
            }else if (value instanceof Set) {
                Set set = (Set) value;
                jacksonRedisTemplate.setAddAll(key, set);
            }else {
                jacksonRedisTemplate.setAdd(key, value);
            }
        }
    },
    ;


    /**
     * 缓存数据类型
     */
    private DataType dataType;
    /**
     * 描述
     */
    private String desc;

    public abstract Object getCache(CacheMethodMeta cacheMethodMeta);

    public abstract void setCache(CacheMethodMeta cacheMethodMeta, Object value);

    /**
     * 根据类型获取策略
     * @param dataType  字段类型
     * @return          转换器
     */
    public static CacheDataStrategy getStrategy(DataType dataType){
        for (CacheDataStrategy value : values()) {
            if (value.getDataType() == dataType) {
                return value;
            }
        }
        return null;
    }
}
