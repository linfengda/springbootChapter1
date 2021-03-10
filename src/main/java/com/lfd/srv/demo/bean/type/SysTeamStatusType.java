package com.lfd.srv.demo.bean.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description
 * @author linfengda
 * @date 2020-12-22 11:36
 */
@AllArgsConstructor
@Getter
public enum SysTeamStatusType {
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

    public static SysTeamStatusType getType(Integer state) {
        for (SysTeamStatusType value : values()) {
            if (value.getCode().equals(state)) {
                return value;
            }
        }
        return null;
    }
}
