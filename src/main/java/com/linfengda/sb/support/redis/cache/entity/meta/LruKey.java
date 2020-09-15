package com.linfengda.sb.support.redis.cache.entity.meta;

import lombok.Data;

/**
 * @description:
 * @author: linfengda
 * @date: 2020-09-16 00:51
 */
@Data
public class LruKey {
    private String key;
    private String lurKey;
}
