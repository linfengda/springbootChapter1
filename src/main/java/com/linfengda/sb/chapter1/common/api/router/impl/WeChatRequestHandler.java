package com.linfengda.sb.chapter1.common.api.router.impl;

import com.linfengda.sb.chapter1.common.api.entity.RequestInfoBO;
import com.linfengda.sb.chapter1.common.api.router.AbstractRequestHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述: WeChat端Handler
 *
 * @author linfengda
 * @create 2019-12-19 17:56
 */
@Slf4j
public class WeChatRequestHandler extends AbstractRequestHandler {

    public WeChatRequestHandler(RequestInfoBO requestInfoBO) {
        super(requestInfoBO);
    }

    @Override
    public void doHandler() throws Exception {
        log.info("WeChat端接口访问，ip={}，url={}，requestParam={}", getRequestInfoBO().getIp(), getRequestInfoBO().getUrl(), getRequestInfoBO().getRequestParam());
    }
}
