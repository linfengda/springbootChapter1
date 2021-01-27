package com.linfengda.sb.support.gateway.acl;

import lombok.AllArgsConstructor;

/**
 * @description 系统白名单
 * @author linfengda
 * @date 2020-12-16 17:08
 */
@AllArgsConstructor
public enum InterceptorWhiteUrl {
    /**
     * org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController#error
     */
    ERROR("/error", "");

    private String url;
    private String desc;

    public static boolean isWhiteUrl(String url) {
        for (InterceptorWhiteUrl whiteUrl : values()) {
            if (whiteUrl.url.equals(url)) {
                return true;
            }
        }
        return false;
    }
}
