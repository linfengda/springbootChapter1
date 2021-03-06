package com.linfengda.sb.support.mybatis.result;

/**
 * mybatis结果集拦截器
 *
 * @author linfengda
 * @date 2021-03-05 16:19
 */
public interface ResultInterceptor {

    /**
     * 结果集拦截
     * @param result mybatis返回查询结果
     */
    void intercept(Object result);
}
