package com.linfengda.sb.support.cache.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 描述: LRU缓存DTO
 *
 * @author: linfengda
 * @date: 2020-07-15 10:52
 */
@Data
@AllArgsConstructor
public class LRUCacheDTO {
    /**
     * 缓存对象
     */
    private Object value;
    /**
     * 最近访问时间
     */
    private Long lastAccessTime;
}
