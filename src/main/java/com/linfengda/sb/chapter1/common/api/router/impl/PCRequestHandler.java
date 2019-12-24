package com.linfengda.sb.chapter1.common.api.router.impl;

import com.linfengda.sb.chapter1.common.api.entity.RequestInfoBO;
import com.linfengda.sb.chapter1.common.api.router.AbstractRequestHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述: PC端Handler
 *
 * @author linfengda
 * @create 2019-12-19 17:55
 */
@Slf4j
public class PCRequestHandler extends AbstractRequestHandler {

    public PCRequestHandler(RequestInfoBO requestInfoBO) {
        super(requestInfoBO);
    }

    @Override
    public void doHandler() throws Exception {
        log.info("PC端接口访问，ip={}，url={}，requestParam={}", getRequestInfoBO().getIp(), getRequestInfoBO().getUrl(), getRequestInfoBO().getRequestParam());
    }
}
