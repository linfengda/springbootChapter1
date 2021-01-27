package com.linfengda.sb.support.gateway.interceptor.impl;

import com.linfengda.sb.support.exception.BusinessException;
import com.linfengda.sb.support.gateway.acl.AuthWhiteUrlList;
import com.linfengda.sb.support.gateway.router.RequestRouter;
import com.linfengda.sb.support.gateway.session.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description 权限拦截
 * @author linfengda
 * @date 2020-09-21 15:21
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor, UserSessionConstant {
    @Resource
    private UserSessionInitializer userSessionInitializer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (AuthWhiteUrlList.isWhiteUrl(request.getRequestURI())) {
            //系统白名单
            return true;
        }
        // 统一登陆校验
        String authToken = request.getHeader(AUTHORIZATION);
        UserSessionBO userSessionBO = userSessionInitializer.initFromPlatform(authToken);
        if (null == userSessionBO) {
            throw new BusinessException("当前用户未登陆！");
        }
        UserSessionHelper.put(userSessionBO);
        // 模块权限校验
        RequestRouter.INSTANCE.doRouter(RequestSessionHelper.get(), (HandlerMethod) handler);
        return true;
    }
}
