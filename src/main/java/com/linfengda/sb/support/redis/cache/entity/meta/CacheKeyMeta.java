package com.linfengda.sb.support.redis.cache.entity.meta;

import lombok.Data;

import java.lang.reflect.Parameter;

/**
 * @description:
 * @author: linfengda
 * @date: 2020-07-22 18:44
 */
@Data
public class CacheKeyMeta {
    /**
     * 参数
     */
    private Parameter parameter;
    /**
     * 参数下标
     */
    private Integer index;
    /**
     * key为空时使用值
     */
    String nullKey;
}
