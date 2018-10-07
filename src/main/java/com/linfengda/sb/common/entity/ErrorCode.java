package com.linfengda.sb.common.entity;

public interface ErrorCode {

    /** 系统错误码 */
    int SYSTEM_ERROR_CODE = -100;

    /** 通用架构系统异常码 */
    int COMMON_ERROR_CODE = -10000;

    /** 通用参数解析异常码 */
    int COMMON_PARAM_ERROR_CODE = -20000;

    /** token超时 重新登录 */
    int LOGIN_TIME_OUT = -102;

    /** 用户名或密码错误 */
    int LOGIN_ERROR = -103;

    /** 无权限 */
    int NO_AUTH = -104;

    /** 响应成功 */
    int SUCCESS = 0;

}

