package com.linfengda.sb.support.gateway.entity;

import lombok.Builder;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

/**
 * @description 请求BO
 * @author linfengda
 * @date 2020-12-16 22:19
 */
@Data
@Builder
public class RequestInfoBO {
    /**
     * traceId
     */
    private String traceId;
    /**
     * 请求url
     */
    private String url;
    /**
     * 请求方式
     */
    private String method;
    /**
     * 请求参数
     */
    private String requestParams;
    /**
     * 请求
     */
    private HttpServletRequest request;
    /**
     * 请求开始时间
     */
    private Long requestTime;

    public String getUriHead(){
        StringBuilder sb = new StringBuilder();
        for (int i = 1;i< url.length();i++){
            char t = url.charAt(i);
            if (t == '/'){
                break;
            }
            sb.append(t);
        }
        return sb.toString();
    }
}
