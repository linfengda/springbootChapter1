package com.linfengda.sb.support.middleware.rabbitmq.service;

import com.linfengda.sb.support.middleware.rabbitmq.helper.ConnectionHelper;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 描述: mq消费者服务
 *
 * @author linfengda
 * @create 2019-04-18 17:15
 */
@Slf4j
public class MqConsumerService {

    public void consumeSimpleMsg(String queue) throws Exception {
        // 1.使用消费回调
        /*Connection connection = ConnectionHelper.getConnection();
        Channel channel = connection.createChannel();
        // 设置消费者最多接收未被ack的消息个数
        channel.basicQos(64);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("接收mq消息：{}", new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(queue, false, consumer);
        TimeUnit.SECONDS.sleep(30);
        channel.close();
        connection.close();*/

        // 2.使用交付回调
        Connection connection = ConnectionHelper.getConnection();
        Channel channel = connection.createChannel();
        // 设置消费者最多接收未被ack的消息个数
        channel.basicQos(64);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            log.info("接收mq消息：{}", message);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        channel.basicConsume(queue, false, deliverCallback, consumerTag -> { });
        TimeUnit.SECONDS.sleep(30);
        channel.close();
        connection.close();
    }

    public void consumeWorkerMsg(String queue, String consumer) throws Exception {
        Connection connection = ConnectionHelper.getConnection();
        Channel channel = connection.createChannel();
        // 消费者只接收1个消息，确认后再接收下一个
        channel.basicQos(1);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            doWork(consumer, message);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        channel.basicConsume(queue, false, deliverCallback, consumerTag -> { });
        TimeUnit.SECONDS.sleep(30);
        channel.close();
        connection.close();
    }






    private void doWork(String consumer, String message) {
        try {
            log.info("{}开始处理{}", consumer, message);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.info("{}处理完成{}", consumer, message);
        }
    }
}
