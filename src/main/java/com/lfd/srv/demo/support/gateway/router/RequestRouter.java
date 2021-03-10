package com.lfd.srv.demo.support.gateway.router;

import com.lfd.srv.demo.support.gateway.entity.RequestSessionBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;

/**
 * 描述: 接口路由（使用委派模式设计）
 *
 * @author linfengda
 * @create 2019-12-19 17:52
 */
@Slf4j
public enum RequestRouter {
    /**
     * 路由
     */
    INSTANCE;

    public void doRouter(RequestSessionBO requestSessionBO, HandlerMethod handlerMethod) throws Exception {
        RequestHandler requestHandler = BizModuleHandlerProvider.provide(requestSessionBO, handlerMethod);
        requestHandler.doHandler();
    }
}
