package com.lfd.soa.srv.demo.support.rabbitmq.service;

import com.lfd.soa.srv.demo.support.rabbitmq.ConnectionHelper;
import com.lfd.soa.srv.demo.support.rabbitmq.QueueVo;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 描述: mq生产者服务
 *
 * @author linfengda
 * @create 2019-04-18 17:14
 */
@Slf4j
public class MqProducerService {


    public void sendDirectMsg(String exchange, List<QueueVo> sendQueues) throws Exception {
        Connection connection = ConnectionHelper.getConnection();
        Channel channel = connection.createChannel();
        // 定义direct类型exchange，该exchange会将消息发送到指定的queue
        channel.exchangeDeclare(exchange, BuiltinExchangeType.DIRECT.getType(), true, false, null);
        // 定义多个queue
        for (QueueVo sendQueue : sendQueues) {
            channel.queueDeclare(sendQueue.getQueue(), true, false, false, null);
            channel.queueBind(sendQueue.getQueue(), exchange, sendQueue.getRoutingKey());
            channel.basicPublish(exchange, sendQueue.getRoutingKey(), MessageProperties.PERSISTENT_TEXT_PLAIN, sendQueue.getMessage().getBytes());
            // DIRECT绑定类型会严格限制发送的routingKey，因此这条消息不会发送
            channel.basicPublish(exchange, sendQueue.getRoutingKey() + ".wrongKey", MessageProperties.PERSISTENT_TEXT_PLAIN, (sendQueue.getMessage() + ".message from wrongKey").getBytes());
            log.info("向{}发送mq消息：{}", sendQueue.getQueue(), sendQueue.getMessage());
        }
        channel.close();
        connection.close();
    }

    public void sendTopicMsg(String exchange, List<QueueVo> sendQueues) throws Exception {
        Connection connection = ConnectionHelper.getConnection();
        Channel channel = connection.createChannel();
        // 定义topic类型exchange，该exchange会将消息发送到匹配的queue
        channel.exchangeDeclare(exchange, BuiltinExchangeType.TOPIC.getType(), true, false, null);
        // 定义多个queue
        for (QueueVo sendQueue : sendQueues) {
            channel.queueDeclare(sendQueue.getQueue(), true, false, false, null);
            channel.queueBind(sendQueue.getQueue(), exchange, sendQueue.getRoutingKey());
            // TOPIC绑定类型会*号匹配发送的routingKey，因此2条消息都会发送
            channel.basicPublish(exchange, sendQueue.getRoutingKey() + ".hello", MessageProperties.PERSISTENT_TEXT_PLAIN, sendQueue.getMessage().getBytes());
            channel.basicPublish(exchange, sendQueue.getRoutingKey() + ".loveWords", MessageProperties.PERSISTENT_TEXT_PLAIN, new String("么么么么么么么么么么哒。").getBytes());
            log.info("向{}发送mq消息：{}", sendQueue.getQueue(), sendQueue.getMessage());
        }
        channel.close();
        connection.close();
    }
}
