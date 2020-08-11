package com.linfengda.sb.chapter1.common.api.router;

import com.linfengda.sb.chapter1.common.api.context.RequestContext;
import com.linfengda.sb.chapter1.common.api.entity.RequestInfoBO;
import com.linfengda.sb.chapter1.common.util.HttpServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;

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

    public Object doRouter(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            // 1.获取请求信息上下文
            RequestInfoBO requestInfoBO = HttpServletUtil.getRequestInfoBO();
            RequestContext.setParam(requestInfoBO);

            // 2.根据url进行路由
            Object result = BizModuleHandlerProvider.provide(requestInfoBO, proceedingJoinPoint).doHandler();
            return result;
        } finally {
            releaseSource();
        }
    }

    /**
     * 释放资源
     */
    private void releaseSource() {
        RequestContext.remove();
    }
}
