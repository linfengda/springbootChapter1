package com.linfengda.sb.support.redis.cache.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述: 支持的缓存key数据类型
 *
 * @author linfengda
 * @create 2019-12-23 17:58
 */
@Getter
@AllArgsConstructor
public enum KeyType {
    /**
     * java.lang.String
     */
    STRING("java.lang.String"),
    /**
     * int
     */
    INT("int"),
    /**
     * java.lang.Integer
     */
    B_INT("java.lang.Integer"),
    /**
     * float
     */
    FLOAT("float"),
    /**
     * java.lang.Float
     */
    B_FLOAT("java.lang.Float"),
    /**
     * double
     */
    DOUBLE("double"),
    /**
     * java.lang.Double
     */
    B_DOUBLE("java.lang.Double"),
    /**
     * long
     */
    LONG("long"),
    /**
     * java.lang.Long
     */
    B_LONG("java.lang.Long"),
    /**
     * boolean
     */
    BOOLEAN("boolean"),
    /**
     * java.lang.Boolean
     */
    B_BOOLEAN("java.lang.Boolean"),
    ;

    private String type;

    public static boolean isBaseType(String type){
        for (KeyType value : values()) {
            if (value.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
