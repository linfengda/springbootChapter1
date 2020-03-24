package com.linfengda.sb.chapter1.common.api.router;

/**
 * 描述: 请求处理委派模式接口，用于请求分流
 *
 * @author linfengda
 * @create 2020-03-24 14:03
 */
public interface RequestHandler {

    /**
     * 处理请求
     * @return  请求处理结果
     * @throws Exception
     */
    Object doHandler() throws Throwable;
}
