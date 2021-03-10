package com.lfd.srv.demo.support.gateway.interceptor;

import com.lfd.srv.demo.Constant;
import com.lfd.srv.demo.support.gateway.acl.InterceptorWhiteUrl;
import com.lfd.srv.demo.support.gateway.session.RequestSession;
import com.lfd.srv.demo.support.gateway.session.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description 将拦截器业务和释放会话信息解耦
 * @author linfengda
 * @date 2020-12-16 17:51
 */
@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            if (!isApiRequest(request, handler)) {
                return true;
            }
            for (HandlerInterceptor handlerInterceptor : InterceptorHolder.handlerInterceptors) {
                if (!handlerInterceptor.preHandle(request, response, handler)) {
                    log.info("拦截器返回false，handlerInterceptor：{}", handlerInterceptor.getClass().getSimpleName());
                    clearSession();
                    return false;
                }
            }
        }catch (Exception e) {
            clearSession();
            throw e;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        try {
            if (!isApiRequest(request, handler)) {
                return;
            }
            for (HandlerInterceptor handlerInterceptor : InterceptorHolder.handlerInterceptors) {
                handlerInterceptor.postHandle(request, response, handler, modelAndView);
            }
        }catch (Exception e) {
            clearSession();
            throw e;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            if (!isApiRequest(request, handler)) {
                return;
            }
            for (HandlerInterceptor handlerInterceptor : InterceptorHolder.handlerInterceptors) {
                handlerInterceptor.afterCompletion(request, response, handler, ex);
            }
        }finally {
            clearSession();
        }
    }

    /**
     * 统一清除会话
     */
    private void clearSession() {
        RequestSession.remove();
        UserSession.remove();
        MDC.remove(Constant.TRACE_ID);
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
        if (InterceptorWhiteUrl.isWhiteUrl(request.getRequestURI())) {
            return false;
        }
        return true;
    }
}
