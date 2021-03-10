package com.lfd.soa.srv.demo.support.gateway.router;

import com.lfd.soa.srv.demo.support.gateway.entity.RequestSessionBO;
import com.lfd.soa.srv.demo.support.gateway.enums.ModuleType;
import lombok.Data;
import org.springframework.web.method.HandlerMethod;

/**
 * 描述: Abstract Request Handler
 *
 * @author linfengda
 * @create 2019-12-19 17:56
 */
@Data
public abstract class AbstractRequestHandler implements RequestHandler {
    /**
     * 模块类型
     */
    protected ModuleType moduleType;
    /**
     * 请求信息BO
     */
    protected RequestSessionBO requestSessionBO;
    /**
     * 请求handle
     */
    protected HandlerMethod handlerMethod;

    public AbstractRequestHandler(RequestSessionBO requestSessionBO, HandlerMethod handlerMethod) {
        this.requestSessionBO = requestSessionBO;
        this.handlerMethod = handlerMethod;
        this.init();
    }

    /**
     * 初始化方法
     */
    abstract protected void init();
}
