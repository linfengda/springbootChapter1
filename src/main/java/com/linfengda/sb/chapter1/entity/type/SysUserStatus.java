package com.linfengda.sb.chapter1.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description
 * @author linfengda
 * @date 2020-12-22 11:35
 */
@AllArgsConstructor
@Getter
public enum SysUserStatus {
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

    public static SysUserStatus getType(Integer state) {
        for (SysUserStatus value : values()) {
            if (value.getCode().equals(state)) {
                return value;
            }
        }
        return null;
    }
}
