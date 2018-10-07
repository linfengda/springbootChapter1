package com.linfengda.sb.support.dao.exception;


import com.linfengda.sb.common.entity.ErrorCode;

/**
 * 描述: persistent exception
 *
 * @author linfengda
 * @create 2018-08-17 16:40
 */
public class DataAccessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataAccessException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public DataAccessException(String msg) {
        super(msg);
        this.msg = msg;
    }

    private int code = ErrorCode.COMMON_ERROR_CODE;

    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
