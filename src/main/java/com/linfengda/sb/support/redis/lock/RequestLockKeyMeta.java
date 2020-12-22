package com.linfengda.sb.support.redis.lock;

import lombok.Data;

import java.lang.reflect.Parameter;

/**
 * @description: 业务锁key注解元数据
 * @author: linfengda
 * @date: 2020-12-22 14:12
 */
@Data
public class RequestLockKeyMeta {
    /**
     * 参数
     */
    private Parameter parameter;
    /**
     * 参数下标
     */
    private Integer index;
}
