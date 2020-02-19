package com.linfengda.sb.chapter1.common.api.router;

import com.linfengda.sb.chapter1.common.api.entity.RequestInfoBO;
import lombok.Data;

/**
 * 描述: Request Handler
 *
 * @author linfengda
 * @create 2019-12-19 17:56
 */
@Data
public abstract class AbstractRequestHandler {
    /**
     * 请求信息BO
     */
    private RequestInfoBO requestInfoBO;

    public AbstractRequestHandler(RequestInfoBO requestInfoBO) {
        this.requestInfoBO = requestInfoBO;
    }

    /**
     * 进行转发
     * @throws Exception
     */
    public abstract void doHandler() throws Exception;
}
