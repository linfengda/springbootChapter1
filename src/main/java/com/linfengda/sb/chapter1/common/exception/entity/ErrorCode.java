package com.linfengda.sb.chapter1.common.exception.entity;

public interface ErrorCode {
    public static final String SYSTEM_ERROR_MSG = "喔哦~系统可能出故障了，请帮忙联系实施人员解救下系统吧！";

    /** 系统错误码 */
    int SYSTEM_ERROR_CODE = -100;

    /** 通用参数解析异常码 */
    int COMMON_PARAM_ERROR_CODE = -200;

    /** 通用持久层异常码 */
    int COMMON_DAO_ERROR_CODE = -100;

    /** 通用分布式锁异常码 */
    int COMMON_LOCK_ERROR_CODE = -300;

    /** 通用缓存框架异常码 */
    int COMMON_CACHE_ERROR_CODE = -400;

    /** URL未匹配到*/
    int NOT_URL = -404;

    /** 响应成功 */
    int SUCCESS = 0;

}

