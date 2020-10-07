package com.linfengda.sb.chapter1.common.auth.router;

import com.linfengda.sb.chapter1.common.auth.entity.bo.RequestInfoBO;
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

    public void doRouter(RequestInfoBO requestInfoBO, HandlerMethod handlerMethod) throws Exception {
        RequestHandler requestHandler = BizModuleHandlerProvider.provide(requestInfoBO, handlerMethod);
        requestHandler.doHandler();
    }
}
