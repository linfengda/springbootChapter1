package com.linfengda.sb.common.exception;

import com.linfengda.sb.common.entity.ErrorCode;
import lombok.Getter;

/**
 * 描述: 业务异常类
 *
 * @author linfengda
 * @create 2019-01-23 13:22
 */
public class BusinessException extends RuntimeException {

    public void BusinessException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public void BusinessException(Integer code, String msg, String detailMsg) {
        this.code = code;
        this.msg = msg;
        this.detailMsg = detailMsg;
    }


    @Getter private int code = ErrorCode.COMMON_ERROR_CODE;

    @Getter private String msg;

    @Getter private String detailMsg;
}
