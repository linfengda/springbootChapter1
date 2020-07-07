package com.linfengda.sb.support.cache.entity.dto;

import com.linfengda.sb.support.cache.entity.meta.CacheMethodMeta;
import com.linfengda.sb.support.cache.entity.type.OperationType;
import lombok.Data;

/**
 * 描述: 缓存数据DTO
 *
 * @author: linfengda
 * @date: 2020-07-06 10:46
 */
@Data
public class CacheDataDTO {
    /**
     * 缓存信息
     */
    private CacheMethodMeta meta;
    /**
     * 缓存操作类型
     */
    private OperationType type;
}
