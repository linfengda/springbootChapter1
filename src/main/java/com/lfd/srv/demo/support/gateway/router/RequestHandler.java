package com.lfd.srv.demo.support.gateway.router;

/**
 * 描述: 请求处理委派模式接口，用于请求分流
 *
 * @author linfengda
 * @create 2020-03-24 14:03
 */
public interface RequestHandler {

    /**
     * 处理请求
     * @throws Exception
     */
    void doHandler() throws Exception;
}
