package com.linfengda.sb.support.cache.entity.type;

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
    OBJECT("object", "对象"),
    /**
     * hash
     */
    HASH("hash", "哈希"),
    /**
     * list
     */
    LIST("list", "列表"),
    /**
     * set
     */
    SET("set", "集合"),
    ;


    /**
     * 类型编码
     */
    private String code;
    /**
     * 类型名称
     */
    private String desc;
}
