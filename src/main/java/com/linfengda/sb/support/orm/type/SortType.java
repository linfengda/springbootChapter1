package com.linfengda.sb.support.orm.type;

import lombok.Getter;

/**
 * 描述: 排序枚举
 *
 * @author linfengda
 * @create 2019-04-12 13:25
 */
public enum SortType {
    DESC("DESC"),
    ASC("ASC");
    @Getter private String value;

    SortType(String value){
        this.value = value;
    }

}
