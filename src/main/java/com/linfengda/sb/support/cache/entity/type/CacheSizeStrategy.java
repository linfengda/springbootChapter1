package com.linfengda.sb.support.cache.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述: 缓存最大数量类型
 *
 * @author: linfengda
 * @date: 2020-07-15 16:08
 */
@Getter
@AllArgsConstructor
public enum CacheSizeStrategy {
    /**
     * 未限制缓存数量
     */
    UN_LIMIT("unLimit", "未限制缓存数量"),
    /**
     * 正常缓存数量
     */
    NORMAL_SIZE("normalSize", "正常缓存数量"),
    /**
     * 超出最大缓存数量
     */
    OVER_SIZE("overSize", "超出最大缓存数量"),
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
