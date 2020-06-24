package com.linfengda.sb.chapter1.common.exception.entity;

/**
 * 描述: 业务异常码
 *
 * @author linfengda
 * @create 2019-01-23 13:22
 */
public interface ErrorCode {

    /** 系统错误码 */
    int SYSTEM_ERROR_CODE = 100;

    /** 通用参数解析异常码 */
    int COMMON_PARAM_ERROR_CODE = 200;

    /** 通用持久层异常码 */
    int COMMON_DAO_ERROR_CODE = 300;

    /** 通用缓存框架异常码 */
    int COMMON_CACHE_ERROR_CODE = 400;

    /** 通用分布式锁异常码 */
    int COMMON_LOCK_ERROR_CODE = 500;

    /** 响应成功 */
    int SUCCESS = 0;
}

