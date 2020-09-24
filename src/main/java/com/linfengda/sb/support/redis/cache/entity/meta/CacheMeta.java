package com.linfengda.sb.support.redis.cache.entity.meta;

import lombok.Data;

/**
 * @description:
 * @author: linfengda
 * @date: 2020-09-24 10:38
 */
@Data
public class CacheMeta {
    /**
     * 是否删除前缀的所有缓存
     * @return
     */
    private Boolean allEntries;
}
