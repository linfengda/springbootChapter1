package com.lfd.srv.demo.remote;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @description
 * @author linfengda
 * @date 2020-09-28 00:20
 */
public class AppKeyInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("appKey", "app-mes");
    }
}
