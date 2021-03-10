package com.lfd.srv.demo.support.mybatis.result;

import com.lfd.srv.demo.support.mybatis.result.impl.BizTimeFieldInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义拦截顺序
 *
 * @author linfengda
 * @date 2021-03-05 16:32
 */
public class ResultInterceptorHolder {
    /**
     * 结果集拦截器列表
     */
    private static final List<ResultInterceptor> RESULT_INTERCEPTOR_LIST = new ArrayList<>(1);
    static {
        // 默认值时间字段拦截器
        RESULT_INTERCEPTOR_LIST.add(new BizTimeFieldInterceptor());
    }

    /**
     * 执行结果集拦截
     * @param obj   处理后的obj
     */
    public static void intercept(Object obj){
        for (ResultInterceptor resultInterceptor : RESULT_INTERCEPTOR_LIST) {
            resultInterceptor.intercept(obj);
        }
    }
}
