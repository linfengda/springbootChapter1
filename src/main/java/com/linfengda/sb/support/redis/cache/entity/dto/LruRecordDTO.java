package com.linfengda.sb.support.redis.cache.entity.dto;

import lombok.Data;

/**
 * 描述: LRU记录缓存DTO
 *
 * @author: linfengda
 * @date: 2020-07-16 15:28
 */
@Data
public class LruRecordDTO {
    /**
     * lur缓存key
     */
    private String key;
    /**
     * 该key的最近访问时间
     */
    private Long lastAccessTime;
}
