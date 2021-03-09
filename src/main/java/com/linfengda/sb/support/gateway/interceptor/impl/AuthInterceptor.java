package com.linfengda.sb.support.gateway.interceptor.impl;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.support.gateway.acl.AuthWhiteUrlList;
import com.linfengda.sb.support.gateway.entity.UserSessionBO;
import com.linfengda.sb.support.gateway.router.RequestRouter;
import com.linfengda.sb.support.gateway.session.RequestSession;
import com.linfengda.sb.support.gateway.session.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description 权限拦截
 * @author linfengda
 * @date 2020-09-21 15:21
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (AuthWhiteUrlList.isWhiteUrl(request.getRequestURI())) {
            //系统白名单
            return true;
        }
        // 统一登陆校验
        String authorization = request.getHeader("AUTHORIZATION");
        UserSessionBO userSessionBO = getUserSessionBO(authorization);
        if (null == userSessionBO) {
            throw new BusinessException("当前用户未登陆！");
        }
        UserSession.put(userSessionBO);
        // 模块权限校验
        RequestRouter.INSTANCE.doRouter(RequestSession.get(), (HandlerMethod) handler);
        return true;
    }

    private UserSessionBO getUserSessionBO(String authorization) {
        return UserSessionBO.builder()
                .userId("123")
                .userName("林丰达")
                .build();
    }
}
