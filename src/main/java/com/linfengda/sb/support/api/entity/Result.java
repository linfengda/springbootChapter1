package com.linfengda.sb.support.api.entity;

import com.linfengda.sb.common.entity.ErrorCode;
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
    //返回的数据
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

}
