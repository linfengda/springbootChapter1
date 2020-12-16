package com.linfengda.sb.chapter1.common.api.acl;

import com.linfengda.sb.chapter1.common.api.entity.enums.WhiteUrlListType;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述: 接口白名单
 *
 * @author linfengda
 * @create 2019-12-17 18:19
 */
public class WhiteUrlList {
    private static final Map<String, WhiteUrlListType> WHITE_URL_SET = new HashMap<>(32);
    static {
        WHITE_URL_SET.put("/login", WhiteUrlListType.ARK);
    }

    public static boolean isWhiteUrl(String url) {
        return WHITE_URL_SET.containsKey(url);
    }
}
