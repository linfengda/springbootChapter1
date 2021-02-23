package com.linfengda.sb.support.schedule.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ErrorHandler;

/**
 * 描述: 异步定时任务异常处理
 *
 * @author linfengda
 * @date 2021-02-04 10:57
 */
@Slf4j
public class ScheduleExceptionHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable throwable) {
        log.error("error in schedule-task-thread: " + throwable);
    }
}
