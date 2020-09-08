package com.linfengda.sb.chapter1.common.exception.handler;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ErrorHandler;

/**
 * 描述: 异步定时任务异常处理
 *
 * @author linfengda
 * @create 2018-11-20 10:36
 */
@Slf4j
public class MySchedulingExceptionHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable throwable) {
        if (throwable.getClass() == BusinessException.class) {
            log.warn(throwable.getMessage());
        } else {
            log.error("[my-scheduler-thread] error in task: ", throwable);
        }
    }
}
