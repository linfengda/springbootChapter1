package com.linfengda.sb.support.cache.entity.meta;

import lombok.Data;

import java.lang.reflect.Parameter;

/**
 * 描述: 缓存key元数据
 *
 * @author linfengda
 * @create 2020-03-30 14:26
 */
@Data
public class CacheKeyMeta {
    /**
     * 参数
     */
    private Parameter parameter;
    /**
     * 参数名称
     */
    private String parameterName;
    /**
     * key是否允许为空
     */
    Boolean nullable;
    /**
     * key为空时使用值
     */
    String nullKey;
}
