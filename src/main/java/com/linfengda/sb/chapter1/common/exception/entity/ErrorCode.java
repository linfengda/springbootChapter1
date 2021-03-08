package com.linfengda.sb.chapter1.common.exception.entity;

/**
 * 描述: 业务异常码
 *
 * @author linfengda
 * @create 2019-01-23 13:22
 */
public interface ErrorCode {

    /** 系统错误码 */
    int UNKNOWN_ERROR_CODE = 100;

    /** 通用参数解析异常码 */
    int PARAM_ERROR_CODE = 200;

    /** 通用持久层异常码 */
    int DAO_ERROR_CODE = 300;

    /** 响应成功 */
    int SUCCESS = 0;
}

