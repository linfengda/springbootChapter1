package com.linfengda.sb.chapter1.common.api.router;

import com.linfengda.sb.chapter1.common.api.entity.RequestInfoBO;
import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 描述: Abstract Request Handler
 *
 * @author linfengda
 * @create 2019-12-19 17:56
 */
@Data
public abstract class AbstractRequestHandler implements RequestHandler {
    /**
     * 请求信息BO
     */
    private RequestInfoBO requestInfoBO;
    /**
     * 请求JoinPoint
     */
    private ProceedingJoinPoint proceedingJoinPoint;

    public AbstractRequestHandler(RequestInfoBO requestInfoBO, ProceedingJoinPoint proceedingJoinPoint) {
        this.requestInfoBO = requestInfoBO;
        this.proceedingJoinPoint = proceedingJoinPoint;
    }
}
