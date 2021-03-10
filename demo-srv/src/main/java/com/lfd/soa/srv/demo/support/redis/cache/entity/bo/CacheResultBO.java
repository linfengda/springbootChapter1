package com.lfd.soa.srv.demo.support.redis.cache.entity.bo;

import lombok.Data;

/**
 * @description 缓存查询结果BO
 * @author linfengda
 * @date 2020-07-24 20:30
 */
@Data
public class CacheResultBO {
    private Boolean hasKey;
    private Object target;
}
