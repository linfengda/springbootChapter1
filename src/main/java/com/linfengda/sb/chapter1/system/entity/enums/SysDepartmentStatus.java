package com.linfengda.sb.chapter1.system.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @author: linfengda
 * @date: 2020-12-22 11:37
 */
@AllArgsConstructor
@Getter
public enum SysDepartmentStatus {
    /**
     * 0：启用
     */
    YES(0, "启用"),
    /**
     * 1：停用
     */
    NO(1, "停用");

    private Integer code;
    private String name;

    public static SysDepartmentStatus getType(Integer state) {
        for (SysDepartmentStatus value : values()) {
            if (value.getCode().equals(state)) {
                return value;
            }
        }
        return null;
    }
}
