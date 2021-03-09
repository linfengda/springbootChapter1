package com.linfengda.sb.support.gateway.router;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.support.gateway.entity.RequestSessionBO;
import com.linfengda.sb.support.gateway.router.impl.PcRequestHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.method.HandlerMethod;

/**
 * 描述: 业务模块请求处理器提供策略
 *
 * @author linfengda
 * @create 2019-12-24 10:32
 */
@Getter
@AllArgsConstructor
public enum BizModuleHandlerProvider {
    /**
     * pc端业务
     */
    PC("pc", "pc端业务") {
        @Override
        public RequestHandler getHandler(RequestSessionBO requestSessionBO, HandlerMethod handlerMethod) {
            return new PcRequestHandler(requestSessionBO, handlerMethod);
        }
    },
    ;

    private String prefix;
    private String name;

    public abstract RequestHandler getHandler(RequestSessionBO requestSessionBO, HandlerMethod handlerMethod);

    /**
     * 获取请求处理器
     * @param requestSessionBO         请求信息BO
     * @param handlerMethod         请求handlerMethod
     * @return                      对应业务模块请求处理器
     */
    public static RequestHandler provide(RequestSessionBO requestSessionBO, HandlerMethod handlerMethod) {
        String uriHeader = requestSessionBO.getUriHead();
        for (BizModuleHandlerProvider value : values()) {
            if (value.getPrefix().equals(uriHeader)) {
                return value.getHandler(requestSessionBO, handlerMethod);
            }
        }
        throw new BusinessException("获取不到请求处理器！uriHeader："+uriHeader);
    }
}
