package com.linfengda.sb.support.redis.lock.meta;

import lombok.Data;

import java.lang.reflect.Field;

/**
 * @description: 业务锁key注解元数据
 * @author: linfengda
 * @date: 2020-12-22 14:12
 */
@Data
public class LockKeyMeta {
    /**
     * key位置
     */
    private Integer keyIndex;
    /**
     * key参数位置
     */
    private Integer keyParameterIndex;
    /**
     * key参数取值字段（{@link com.linfengda.sb.support.redis.lock.annotation.BusinessLock}注解在dto字段）
     */
    private Field keyField;
}
