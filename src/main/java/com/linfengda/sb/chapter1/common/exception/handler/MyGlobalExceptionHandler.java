package com.linfengda.sb.chapter1.common.exception.handler;

import com.linfengda.sb.chapter1.common.entity.ErrorCode;
import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.chapter1.common.exception.DistributedLockException;
import com.linfengda.sb.support.api.entity.Result;
//import com.linfengda.sb.support.dao.exception.DataAccessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述: controller统一异常处理
 *
 * @author linfengda
 * @create 2018-11-20 10:36
 */
@Slf4j
@ControllerAdvice
public class MyGlobalExceptionHandler {
    private static final Result RESULT_404 = new Result(ErrorCode.NOT_URL, "请求地址未找到.");

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result defaultErrorHandler(Exception e) {
        Result result;
        /*if (e instanceof DataAccessException) {
            DataAccessException dataAccessException = (DataAccessException) e;
            result = new Result(dataAccessException.getCode(), dataAccessException.getMsg());
            log.warn(dataAccessException.getMsg());
        } else*/ if (e instanceof DistributedLockException) {
            DistributedLockException distributedLockException = (DistributedLockException) e;
            result = new Result(distributedLockException.getCode(), distributedLockException.getMsg());
            log.warn(distributedLockException.getMsg());
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            log.warn("404找不到URL:未知请求与方法", e);
            return RESULT_404;
        } else if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            if (StringUtils.isNotEmpty(businessException.getDetailMsg())) {
                result = new Result(businessException.getCode(), businessException.getMsg(), businessException.getDetailMsg());
            } else {
                result = new Result(businessException.getCode(), businessException.getMsg());
            }
        } else {
            log.error("error info:", e);
            result = new Result(ErrorCode.SYSTEM_ERROR_CODE, ErrorCode.SYSTEM_ERROR_MSG);
        }
        return result;
    }
}
