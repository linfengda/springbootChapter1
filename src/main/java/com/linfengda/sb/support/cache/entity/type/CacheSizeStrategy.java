package com.linfengda.sb.support.cache.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述: 缓存最大数量策略类型
 *
 * @author: linfengda
 * @date: 2020-07-15 16:08
 */
@Getter
@AllArgsConstructor
public enum CacheSizeStrategy {
    /**
     * 正常缓存数量
     */
    NORMAL("normal", "正常缓存数量"),
    /**
     * 达到缓存最大数量：抛弃
     */
    ABANDON("abandon", "达到缓存最大数量：抛弃"),
    /**
     * 达到缓存最大数量：LRU淘汰
     */
    LRU("lru", "达到缓存最大数量：LRU淘汰"),
    ;

    /**
     * 类型编码
     */
    private String code;
    /**
     * 类型名称
     */
    private String name;
}
