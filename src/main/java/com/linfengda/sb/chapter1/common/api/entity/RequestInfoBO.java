package com.linfengda.sb.chapter1.common.api.entity;

import lombok.Data;

/**
 * 描述: 接口请求信息BO
 *
 * @author linfengda
 * @create 2019-12-24 10:52
 */
@Data
public class RequestInfoBO {
    /**
     * ip
     */
    private String ip;
    /**
     * url
     */
    private String url;
    /**
     * 请求参数
     */
    private RequestParam requestParam;
}
