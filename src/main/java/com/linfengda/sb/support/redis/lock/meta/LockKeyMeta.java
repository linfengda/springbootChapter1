package com.linfengda.sb.support.redis.lock.meta;

import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

/**
 * @description: 业务锁key注解元数据
 * @author: linfengda
 * @date: 2020-12-22 14:12
 */
@Data
public class LockKeyMeta {
    /**
     * key参数
     */
    private Parameter keyParameter;
    /**
     * key参数下标
     */
    private Integer keyParameterIndex;
    /**
     * key域
     */
    private Field keyField;
    /**
     * key下标
     */
    private Integer keyIndex;
}
