package com.linfengda.sb.support.dao.exception;


import com.linfengda.sb.chapter1.common.entity.ErrorCode;
import lombok.Getter;

/**
 * 描述: 持久化异常
 *
 * @author linfengda
 * @create 2018-08-17 16:40
 */
public class DataAccessException extends RuntimeException {

    public DataAccessException(String msg) {
        this.msg = msg;
    }

    public DataAccessException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Getter private int code = ErrorCode.COMMON_DAO_ERROR_CODE;

    @Getter private String msg;
}
