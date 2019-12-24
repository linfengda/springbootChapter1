package com.linfengda.sb.chapter1.common.api.router;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述: 业务模块类型
 *
 * @author linfengda
 * @create 2019-12-24 10:32
 */
@Getter
@AllArgsConstructor
public enum BizModuleType {
    /**
     * pc端业务
     */
    PC("/pc", "pc端业务"),
    /**
     * 微信端业务
     */
    WeChat("/weChat", "微信端业务"),
    ;

    private String prefix;
    private String name;

    public static BizModuleType getModuleType(String url) {
        for (BizModuleType value : values()) {
            if (url.startsWith(value.getPrefix())) {
                return value;
            }
        }
        return null;
    }
}
