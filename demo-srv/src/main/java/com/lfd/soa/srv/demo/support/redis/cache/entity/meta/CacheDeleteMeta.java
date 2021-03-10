package com.lfd.soa.srv.demo.support.redis.cache.entity.meta;

import lombok.Data;

import java.util.List;

/**
 * @description
 * @author linfengda
 * @date 2020-07-22 18:39
 */
@Data
public class CacheDeleteMeta extends CacheMeta {
    /**
     * 删除列表
     */
    private List<CacheMethodMeta> deleteMetas;
}
