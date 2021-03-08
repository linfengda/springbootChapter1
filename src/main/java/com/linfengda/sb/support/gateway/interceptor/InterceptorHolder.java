package com.linfengda.sb.support.gateway.interceptor;

import com.linfengda.sb.chapter1.common.util.SpringUtil;
import com.linfengda.sb.support.gateway.interceptor.impl.ApiCallInterceptor;
import com.linfengda.sb.support.gateway.interceptor.impl.AuthInterceptor;
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
