package com.linfengda.sb.support.dao.entity;

import lombok.Getter;

/**
 * 排序关键字枚举
 * @author linfengda
 */
public enum SortType {
    DESC("DESC"),
    ASC("ASC");
    @Getter private String value;

    SortType(String value){
        this.value = value;
    }

}
