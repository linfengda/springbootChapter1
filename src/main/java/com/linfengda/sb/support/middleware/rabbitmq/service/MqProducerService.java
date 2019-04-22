package com.linfengda.sb.support.middleware.rabbitmq.service;

import com.linfengda.sb.support.middleware.rabbitmq.QueueVo;
import com.linfengda.sb.support.middleware.rabbitmq.helper.ConnectionHelper;
import com.rabbitmq.client.BuiltinExchangeType;
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
        messages.add("锄禾日当午，");
        messages.add("汗滴禾下土，");
        messages.add("谁知盘中餐，");
        messages.add("粒粒皆辛苦。");

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
            log.info("发送mq任务：{}", message);
        }
        channel.close();
        connection.close();
    }

    public void sendFanoutMsg(String exchange, List<String> queues) throws Exception {
        String message = "公告：全军出击！";
        Connection connection = ConnectionHelper.getConnection();
        Channel channel = connection.createChannel();
        // 定义fanout类型exchange，该exchange会将消息发送到所有绑定的queue
        channel.exchangeDeclare(exchange, BuiltinExchangeType.FANOUT.getType(), true, false, null);
        // 定义多个queue
        for (String queue : queues) {
            channel.queueDeclare(queue, true, false, false, null);
            channel.queueBind(queue, exchange, "");
        }
        // 发布消息
        channel.basicPublish(exchange, "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        log.info("发送mq公告：{}", message);
        channel.close();
        connection.close();
    }

    public void sendDirectMsg(String exchange, List<QueueVo> queueVos) throws Exception {
        Connection connection = ConnectionHelper.getConnection();
        Channel channel = connection.createChannel();
        // 定义direct类型exchange，该exchange会将消息发送到指定的queue
        channel.exchangeDeclare(exchange, BuiltinExchangeType.DIRECT.getType(), true, false, null);
        // 定义多个queue
        for (QueueVo queueVo : queueVos) {
            channel.queueDeclare(queueVo.getQueue(), true, false, false, null);
            channel.queueBind(queueVo.getQueue(), exchange, queueVo.getRoutingKey());
            channel.basicPublish(exchange, queueVo.getRoutingKey(), MessageProperties.PERSISTENT_TEXT_PLAIN, queueVo.getMessage().getBytes());
            log.info("向[{}]发送mq消息：{}", queueVo.getQueue(), queueVo.getMessage());
        }
        channel.close();
        connection.close();
    }
}
