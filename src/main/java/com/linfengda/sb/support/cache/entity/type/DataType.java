package com.linfengda.sb.support.cache.entity.type;

import com.linfengda.sb.support.cache.config.RedisSupportHolder;
import com.linfengda.sb.support.cache.handler.strategy.CacheStrategy;
import com.linfengda.sb.support.cache.handler.strategy.impl.HashCacheStrategy;
import com.linfengda.sb.support.cache.handler.strategy.impl.ListCacheStrategy;
import com.linfengda.sb.support.cache.handler.strategy.impl.ObjCacheStrategy;
import com.linfengda.sb.support.cache.handler.strategy.impl.SetCacheStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 缓存数据类型
 * @author: linfengda
 * @date: 2020-06-24 19:20
 */
@Getter
@AllArgsConstructor
public enum DataType {
    /**
     * object
     */
    OBJECT("object", "对象") {
        @Override
        public CacheStrategy getStrategy() {
            ObjCacheStrategy objCacheStrategy = new ObjCacheStrategy();
            objCacheStrategy.setSimpleRedisTemplate(RedisSupportHolder.getSimpleRedisTemplate());
            return objCacheStrategy;
        }
    },
    /**
     * hash
     */
    HASH("hash", "哈希") {
        @Override
        public CacheStrategy getStrategy() {
            HashCacheStrategy hashCacheStrategy = new HashCacheStrategy();
            hashCacheStrategy.setSimpleRedisTemplate(RedisSupportHolder.getSimpleRedisTemplate());
            return hashCacheStrategy;
        }
    },
    /**
     * list
     */
    LIST("list", "列表") {
        @Override
        public CacheStrategy getStrategy() {
            ListCacheStrategy listCacheStrategy = new ListCacheStrategy();
            listCacheStrategy.setSimpleRedisTemplate(RedisSupportHolder.getSimpleRedisTemplate());
            return listCacheStrategy;
        }
    },
    /**
     * set
     */
    SET("set", "集合") {
        @Override
        public CacheStrategy getStrategy() {
            SetCacheStrategy setCacheStrategy = new SetCacheStrategy();
            setCacheStrategy.setSimpleRedisTemplate(RedisSupportHolder.getSimpleRedisTemplate());
            return setCacheStrategy;
        }
    },
    ;


    /**
     * 类型编码
     */
    private String code;
    /**
     * 类型名称
     */
    private String desc;

    public abstract CacheStrategy getStrategy();
}
