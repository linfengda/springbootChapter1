package com.linfengda.sb.support.gateway.router;

import com.linfengda.sb.support.exception.BusinessException;
import com.linfengda.sb.support.gateway.router.impl.PcRequestHandler;
import com.linfengda.sb.support.gateway.entity.RequestInfoBO;
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
        public RequestHandler getHandler(RequestInfoBO requestInfoBO, HandlerMethod handlerMethod) {
            return new PcRequestHandler(requestInfoBO, handlerMethod);
        }
    },
    ;

    private String prefix;
    private String name;

    public abstract RequestHandler getHandler(RequestInfoBO requestInfoBO, HandlerMethod handlerMethod);

    /**
     * 获取请求处理器
     * @param requestInfoBO         请求信息BO
     * @param handlerMethod         请求handlerMethod
     * @return                      对应业务模块请求处理器
     */
    public static RequestHandler provide(RequestInfoBO requestInfoBO, HandlerMethod handlerMethod) {
        String uriHeader = requestInfoBO.getUriHead();
        for (BizModuleHandlerProvider value : values()) {
            if (value.getPrefix().equals(uriHeader)) {
                return value.getHandler(requestInfoBO, handlerMethod);
            }
        }
        throw new BusinessException("获取不到请求处理器！uriHeader："+uriHeader);
    }
}
