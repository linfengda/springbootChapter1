package com.linfengda.sb.support.api.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 描述: HTTP请求处理工具类
 *
 * @author linfengda
 * @create 2018-08-19 23:53
 */
public enum HttpMethod {
    GET("get"),
    POST("post");

    @Setter @Getter private String value;

    HttpMethod(String value){
        this.value = value;
    }

    public static HttpMethod getHttpMethod(String value){
        for (HttpMethod method:values()) {
            if (method.getValue().equals(value)){
                return  method;
            }
        }
        return  null;
    }
}
