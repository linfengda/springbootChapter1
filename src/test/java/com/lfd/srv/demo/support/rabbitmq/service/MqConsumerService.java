package com.lfd.srv.demo.support.rabbitmq.service;

import com.lfd.srv.demo.support.rabbitmq.ConnectionHelper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 描述: mq消费者服务
 *
 * @author linfengda
 * @create 2019-04-18 17:15
 */
@Slf4j
public class MqConsumerService {


    public void consumeDirectMsg(String queue) throws Exception {
        Connection connection = ConnectionHelper.getConnection();
        Channel channel = connection.createChannel();
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            log.info("{}接收mq消息：{}", queue, message);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        channel.basicConsume(queue, false, deliverCallback, consumerTag -> { });
        TimeUnit.SECONDS.sleep(30);
        channel.close();
        connection.close();
    }

    public void consumeTopicMsg(String exchange, String queue, String matchKey) throws Exception {
        Connection connection = ConnectionHelper.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange, BuiltinExchangeType.TOPIC.getType(), true, false, null);
        channel.queueDeclare(queue, true, false, false, null);
        channel.queueBind(queue, exchange, matchKey);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            log.info("[{}]接收mq消息：{}", queue, message);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        channel.basicConsume(queue, false, deliverCallback, consumerTag -> { });
        TimeUnit.SECONDS.sleep(30);
        channel.close();
        connection.close();
    }
}
