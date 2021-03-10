package com.lfd.srv.demo.support.gateway.interceptor;

import com.lfd.srv.demo.support.gateway.interceptor.impl.AuthInterceptor;
import com.lfd.common.util.SpringUtil;
import com.lfd.srv.demo.support.gateway.interceptor.impl.ApiCallInterceptor;
import lombok.Getter;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @author linfengda
 * @date 2020-12-16 17:46
 */
@Getter
public class InterceptorHolder {
    public static final List<HandlerInterceptor> handlerInterceptors = new ArrayList<>();
    static {
        handlerInterceptors.add(SpringUtil.getBean(ApiCallInterceptor.class));
        handlerInterceptors.add(SpringUtil.getBean(AuthInterceptor.class));
    }
}
