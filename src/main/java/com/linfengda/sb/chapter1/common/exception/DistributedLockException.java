package com.linfengda.sb.chapter1.common.exception;

import com.linfengda.sb.chapter1.common.entity.ErrorCode;
import lombok.Getter;

/**
 * 描述: 分布式锁异常
 *
 * @author linfengda
 * @create 2018-11-20 10:40
 */
@Getter
public class DistributedLockException extends RuntimeException {

    public DistributedLockException(String msg) {
        this.msg = msg;
    }

    public DistributedLockException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code = ErrorCode.COMMON_LOCK_ERROR_CODE;

    private String msg;
}
