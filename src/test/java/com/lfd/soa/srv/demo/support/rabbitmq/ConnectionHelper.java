package com.lfd.soa.srv.demo.support.rabbitmq;

import com.lfd.soa.common.exception.BusinessException;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述: 获取连接
 *
 * @author linfengda
 * @create 2019-04-18 17:02
 */
@Slf4j
public class ConnectionHelper {
    private static final String host = "115.28.49.96";
    private static final Integer port = 5672;
    private static final String userName = "backend";
    private static final String password = "stylewe";

    public static synchronized Connection getConnection() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            factory.setUsername(userName);
            factory.setPassword(password);
            return factory.newConnection();
        } catch (Exception e) {
            log.error("获取rabbitmq connection出错：", e);
            throw new BusinessException("获取rabbitmq connection出错：" + e);
        }
    }
}
