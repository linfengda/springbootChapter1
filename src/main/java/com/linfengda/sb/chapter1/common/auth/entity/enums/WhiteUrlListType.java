package com.linfengda.sb.chapter1.common.auth.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 白名单接口类型
 * @author: linfengda
 * @date: 2020-09-23 15:42
 */
@Getter
@AllArgsConstructor
public enum WhiteUrlListType {
    /**
     * 本系统
     */
    ARK("ark", "本系统", 1, "system"),
    /**
     * HUNTER系统
     */
    HUNTER("hunter", "HUNTER系统", 2, "hunterSystem"),
    /**
     * MES系统
     */
    MES("mes", "MES系统", 3, "mesSystem"),
    ;

    /**
     * 白名单类型
     */
    private String code;
    /**
     * 白名单所属系统
     */
    private String system;
    /**
     * 白名单所属系统默认用户uid
     */
    private Integer systemUid;
    /**
     * 白名单所属系统默认用户名称
     */
    private String systemName;
}
