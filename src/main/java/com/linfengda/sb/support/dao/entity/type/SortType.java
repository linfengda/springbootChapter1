package com.linfengda.sb.support.dao.entity.type;

import lombok.Getter;

/**
 * 排序关键字枚举
 * @author liugenhua
 */
public enum SortType {
    DESC("DESC"),
    ASC("ASC");
    @Getter private String value;

    SortType(String value){
        this.value = value;
    }

}
