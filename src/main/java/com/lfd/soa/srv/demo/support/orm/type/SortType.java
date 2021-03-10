package com.lfd.soa.srv.demo.support.orm.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述: 排序枚举
 *
 * @author linfengda
 * @create 2019-04-12 13:25
 */
@Getter
@AllArgsConstructor
public enum SortType {
    /**
     * 倒序
     */
    DESC("DESC"),
    /**
     * 正序
     */
    ASC("ASC");

    private String value;
}
