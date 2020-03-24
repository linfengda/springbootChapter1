package com.linfengda.sb.chapter1.common.api.router;

import com.linfengda.sb.chapter1.common.api.entity.RequestInfoBO;
import com.linfengda.sb.chapter1.common.api.router.impl.PCRequestHandler;
import com.linfengda.sb.chapter1.common.api.router.impl.WeChatRequestHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.aspectj.lang.ProceedingJoinPoint;

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
        public RequestHandler getHandler(RequestInfoBO requestInfoBO, ProceedingJoinPoint proceedingJoinPoint) {
            return new PCRequestHandler(requestInfoBO, proceedingJoinPoint);
        }
    },
    /**
     * 微信端业务
     */
    WeChat("weChat", "微信端业务") {
        @Override
        public RequestHandler getHandler(RequestInfoBO requestInfoBO, ProceedingJoinPoint proceedingJoinPoint) {
            return new WeChatRequestHandler(requestInfoBO, proceedingJoinPoint);
        }
    },
    ;

    private String prefix;
    private String name;

    public abstract RequestHandler getHandler(RequestInfoBO requestInfoBO, ProceedingJoinPoint proceedingJoinPoint);

    /**
     * 获取请求处理器
     * @param requestInfoBO         请求信息BO
     * @param proceedingJoinPoint   请求JoinPoint
     * @return                      对应业务模块请求处理器
     */
    public static RequestHandler provide(RequestInfoBO requestInfoBO, ProceedingJoinPoint proceedingJoinPoint) {
        String uriHeader = requestInfoBO.getUriHead();
        for (BizModuleHandlerProvider value : values()) {
            if (value.getPrefix().equals(uriHeader)) {
                return value.getHandler(requestInfoBO, proceedingJoinPoint);
            }
        }
        return null;
    }
}
