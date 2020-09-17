package com.linfengda.sb.support.redis.cache.entity.meta;

import lombok.Data;

/**
 * @description:
 * @author: linfengda
 * @date: 2020-09-17 18:39
 */
@Data
public class CacheDeleteMeta {
    /**
     * 是否删除前缀的所有缓存
     * @return
     */
    private Boolean allEntries;
}
