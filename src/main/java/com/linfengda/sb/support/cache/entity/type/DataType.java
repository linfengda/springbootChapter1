package com.linfengda.sb.support.cache.entity.type;

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
            return new ObjCacheStrategy();
        }
    },
    /**
     * hash
     */
    HASH("hash", "哈希") {
        @Override
        public CacheStrategy getStrategy() {
            return new HashCacheStrategy();
        }
    },
    /**
     * list
     */
    LIST("list", "列表") {
        @Override
        public CacheStrategy getStrategy() {
            return new ListCacheStrategy();
        }
    },
    /**
     * set
     */
    SET("set", "集合") {
        @Override
        public CacheStrategy getStrategy() {
            return new SetCacheStrategy();
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
