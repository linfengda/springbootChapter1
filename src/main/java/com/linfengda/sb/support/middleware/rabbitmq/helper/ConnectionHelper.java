package com.linfengda.sb.support.middleware.rabbitmq.helper;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
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

    public static synchronized Connection getConnection() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("119.23.181.11");
            factory.setPort(5672);
            factory.setUsername("linfengda");
            factory.setPassword("123456");
            return factory.newConnection();
        } catch (Exception e) {
            log.error("获取rabbitmq connection出错：", e);
            throw new BusinessException("获取rabbitmq connection出错：" + e);
        }
    }
}
