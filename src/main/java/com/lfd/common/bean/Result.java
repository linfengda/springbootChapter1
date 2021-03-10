package com.lfd.common.bean;

import com.lfd.common.exception.entity.ErrorCode;
import lombok.Data;

/**
 * 描述: 返回信息
 *
 * @author linfengda
 * @create 2018-08-19 22:51
 */
@Data
public class Result {
    private int code = ErrorCode.SUCCESS;
    private String msg = "success";
    private Object info;

    public Result() {

    }

    public Result(Object info) {
        this.info = info;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, Object info) {
        this.code = code;
        this.msg = msg;
        this.info = info;
    }
}
