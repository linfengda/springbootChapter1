package com.linfengda.sb.support.redis.cache.entity.meta;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: linfengda
 * @date: 2020-09-17 18:39
 */
@Data
public class CacheDeleteMeta extends CacheMeta {
    /**
     * 删除列表
     */
    private List<CacheMethodMeta> deleteMetas;
}
