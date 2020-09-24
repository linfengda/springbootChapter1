package com.linfengda.sb.chapter1.common.api;

import com.linfengda.sb.chapter1.common.api.entity.RequestInfoBO;
import com.linfengda.sb.chapter1.common.api.router.RequestRouter;
import com.linfengda.sb.chapter1.common.api.util.IpUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 权限拦截
 * @author: linfengda
 * @date: 2020-09-21 15:21
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestInfoBO requestInfoBO = getRequestInfoBO(request);
        RequestRouter.INSTANCE.doRouter(requestInfoBO, (HandlerMethod) handler);
        return super.preHandle(request, response, handler);
    }

    /**
     * 获取请求BO
     * @param request
     * @return  请求BO
     * @throws Exception
     */
    public RequestInfoBO getRequestInfoBO(HttpServletRequest request) {
        RequestInfoBO requestInfoBO = new RequestInfoBO();
        requestInfoBO.setRequest(request);
        requestInfoBO.setIp(IpUtil.getIp(request));
        requestInfoBO.setUrl(request.getRequestURI());
        return requestInfoBO;
    }
}