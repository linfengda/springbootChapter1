package com.linfengda.sb.chapter1.common.exception.handler;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.chapter1.common.exception.DistributedLockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * 描述: 异步线程池异常处理
 *
 * @author linfengda
 * @create 2018-11-20 11:12
 */
@Slf4j
public class MyAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
        // 暂时未作任何个性化处理，使用spring boot默认实现
        if (throwable.getClass() == DistributedLockException.class) {
            log.warn(throwable.getMessage());
        } else if (throwable.getClass() == BusinessException.class) {
            log.warn(throwable.getMessage());
        } else {
            log.error(String.format("[my-task-thread] Unexpected error occurred invoking async method '%s'.", method), throwable);
        }
    }
}
