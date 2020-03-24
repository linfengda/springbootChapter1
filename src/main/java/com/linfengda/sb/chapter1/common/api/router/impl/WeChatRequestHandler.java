package com.linfengda.sb.chapter1.common.api.router.impl;

import com.linfengda.sb.chapter1.common.api.entity.RequestInfoBO;
import com.linfengda.sb.chapter1.common.api.router.AbstractRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 描述: WeChat端Handler
 *
 * @author linfengda
 * @create 2019-12-19 17:56
 */
@Slf4j
public class WeChatRequestHandler extends AbstractRequestHandler {

    public WeChatRequestHandler(RequestInfoBO requestInfoBO, ProceedingJoinPoint proceedingJoinPoint) {
        super(requestInfoBO, proceedingJoinPoint);
    }

    @Override
    public Object doHandler() throws Throwable {
        log.info("WeChat端接口访问，ip={}，url={}，requestParam={}", getRequestInfoBO().getIp(), getRequestInfoBO().getUrl(), getRequestInfoBO().getRequestParam());
        return getProceedingJoinPoint().proceed();
    }
}
