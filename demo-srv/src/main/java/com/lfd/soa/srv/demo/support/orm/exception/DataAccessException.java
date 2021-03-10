package com.lfd.soa.srv.demo.support.orm.exception;


import com.lfd.soa.common.constants.ErrorCode;
import lombok.Getter;

/**
 * 描述: 持久化异常
 *
 * @author linfengda
 * @create 2018-08-17 16:40
 */
@Getter
public class DataAccessException extends RuntimeException {

    public DataAccessException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public DataAccessException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    private int code = ErrorCode.DAO_ERROR_CODE;

    private String msg;
}
