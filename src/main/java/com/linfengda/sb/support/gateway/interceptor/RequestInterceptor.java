package com.linfengda.sb.support.gateway.interceptor;

import com.linfengda.sb.support.gateway.acl.SystemWhiteUrl;
import com.linfengda.sb.support.gateway.session.UserSessionHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: linfengda
 * @date: 2020-12-16 17:51
 */
@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!isApiRequest(request, handler)) {
            return true;
        }
        for (HandlerInterceptor handlerInterceptor : InterceptorHolder.handlerInterceptors) {
            if (!handlerInterceptor.preHandle(request, response, handler)) {
                log.info("handlerInterceptor：{}返回false", handlerInterceptor.getClass().getSimpleName());
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (!isApiRequest(request, handler)) {
            return;
        }
        for (HandlerInterceptor handlerInterceptor : InterceptorHolder.handlerInterceptors) {
            handlerInterceptor.postHandle(request, response, handler, modelAndView);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (!isApiRequest(request, handler)) {
            return;
        }
        try {
            for (HandlerInterceptor handlerInterceptor : InterceptorHolder.handlerInterceptors) {
                handlerInterceptor.afterCompletion(request, response, handler, ex);
            }
        }finally {
            UserSessionHelper.remove();
        }
    }

    /**
     * 是否业务接口请求
     * @param request	请求
     * @param handler	请求handler
     * @return 			true：是，false：否
     */
    private boolean isApiRequest(HttpServletRequest request, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            //只处理Controller方法
            return false;
        }
        if (SystemWhiteUrl.isWhiteUrl(request.getRequestURI())) {
            return false;
        }
        return true;
    }
}
