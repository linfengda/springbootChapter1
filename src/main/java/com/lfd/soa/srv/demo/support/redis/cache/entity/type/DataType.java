package com.lfd.soa.srv.demo.support.redis.cache.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description redis缓存数据类型
 * @author linfengda
 * @date 2020-07-26 19:20
 */
@Getter
@AllArgsConstructor
public enum DataType {
    /**
     * object
     */
    OBJECT("java.lang.object", "对象"),
    /**
     * hash
     */
    HASH("java.lang.object", "哈希"),
    /**
     * list
     */
    LIST("java.util.List", "列表"),
    /**
     * set
     */
    SET("java.util.Set", "集合"),
    ;


    /**
     * java类型
     */
    private String javaType;
    /**
     * redis类型
     */
    private String redisType;
}
