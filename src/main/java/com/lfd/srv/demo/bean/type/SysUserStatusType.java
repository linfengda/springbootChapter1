package com.lfd.srv.demo.bean.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description
 * @author linfengda
 * @date 2020-12-22 11:35
 */
@AllArgsConstructor
@Getter
public enum SysUserStatusType {
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

    public static SysUserStatusType getType(Integer state) {
        for (SysUserStatusType value : values()) {
            if (value.getCode().equals(state)) {
                return value;
            }
        }
        return null;
    }
}
