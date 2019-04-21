package com.linfengda.sb.support.middleware.rabbitmq.service;

import com.linfengda.sb.support.middleware.rabbitmq.helper.ConnectionHelper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: mq生产者服务
 *
 * @author linfengda
 * @create 2019-04-18 17:14
 */
@Slf4j
public class MqProducerService {

    public void sendSimpleMsg(String queue) throws Exception {
        List<String> messages = new ArrayList<>();
        messages.add("儿子啊儿子，");
        messages.add("我是你爸爸，");
        messages.add("你过来，坐下，");
        messages.add("咱爷俩来谈个话");

        Connection connection = ConnectionHelper.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queue, true, false, false, null);
        for (String message : messages) {
            channel.basicPublish("", queue, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            log.info("发送mq消息：{}", message);
        }
        channel.close();
        connection.close();
    }

    public void sendWorkerMsg(String queue) throws Exception {
        List<String> messages = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            messages.add("任务" + i);
        }

        Connection connection = ConnectionHelper.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queue, true, false, false, null);
        for (String message : messages) {
            channel.basicPublish("", queue, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            log.info("发送mq消息：{}", message);
        }
        channel.close();
        connection.close();
    }
}
