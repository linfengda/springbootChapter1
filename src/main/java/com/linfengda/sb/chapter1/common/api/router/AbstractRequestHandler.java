package com.linfengda.sb.chapter1.common.api.router;

import com.linfengda.sb.chapter1.common.api.entity.bo.RequestInfoBO;
import com.linfengda.sb.chapter1.common.api.entity.enums.ModuleType;
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
    protected RequestInfoBO requestInfoBO;
    /**
     * 请求handle
     */
    protected HandlerMethod handlerMethod;

    public AbstractRequestHandler(RequestInfoBO requestInfoBO, HandlerMethod handlerMethod) {
        this.requestInfoBO = requestInfoBO;
        this.handlerMethod = handlerMethod;
        this.init();
    }

    /**
     * 初始化方法
     */
    abstract protected void init();
}
