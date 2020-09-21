package com.linfengda.sb.chapter1.common.api.router.impl;

import com.linfengda.sb.chapter1.common.api.entity.RequestInfoBO;
import com.linfengda.sb.chapter1.common.api.router.AbstractRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;

/**
 * 描述: PC端Handler
 *
 * @author linfengda
 * @create 2019-12-19 17:55
 */
@Slf4j
public class PcRequestHandler extends AbstractRequestHandler {

    public PcRequestHandler(RequestInfoBO requestInfoBO, HandlerMethod handlerMethod) {
        super(requestInfoBO, handlerMethod);
    }

    @Override
    public void doHandler() throws Exception {
        log.info("PC端接口访问，ip={}，url={}", getRequestInfoBO().getIp(), getRequestInfoBO().getUrl());
    }
}
