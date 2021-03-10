package com.lfd.srv.demo.bean.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description
 * @author linfengda
 * @date 2020-08-18 13:53
 */
@Getter
@AllArgsConstructor
public enum SysTeamType {
    /**
     * 1：技术
     */
    TECH(1, "技术"),
    /**
     * 2：买手
     */
    BUYER(2, "买手"),
    /**
     * 3：设计
     */
    DESIGNER(3, "设计"),
    /**
     * 4：打版
     */
    PLATE(4, "打版"),
    /**
     * 5：摄影
     */
    PHOTOGRAPHY(5, "摄影"),
    /**
     * 6：跟单
     */
    ORDER_FOLLOW(6, "跟单"),
    ;

    private Integer code;
    private String name;

    public static SysTeamType getType(Integer state) {
        for (SysTeamType value : values()) {
            if (value.getCode().equals(state)) {
                return value;
            }
        }
        return null;
    }
}
