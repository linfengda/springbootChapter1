package com.linfengda.sb.support.redis.lock.meta;

import lombok.Data;

import java.util.List;

/**
 * @description 业务锁方法注解元数据
 * @author linfengda
 * @date 2020-12-22 22:07
 */
@Data
public class LockMethodMeta {
    /**
     * 业务锁key前缀
     */
    private String prefix;
    /**
     * 业务锁key列表
     */
    private List<LockKeyMeta> lockKeys;
}
