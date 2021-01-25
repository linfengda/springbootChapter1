package com.linfengda.sb.support.rabbitmq;

import com.linfengda.sb.support.exception.BusinessException;
import com.linfengda.sb.chapter1.common.thread.ThreadPoolHelper;
import com.linfengda.sb.support.rabbitmq.service.MqConsumerService;
import com.linfengda.sb.support.rabbitmq.service.MqProducerService;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 描述: rabbitmq测试
 *
 * @author linfengda
 * @create 2019-04-16 15:24
 */
@Slf4j
@RunWith(JUnit4.class)
public class RabbitmqTest {
    private static ThreadPoolTaskExecutor executor = ThreadPoolHelper.initThreadPool(10, 50, "test-thread");


    /**
     * 测试单个消费者
     * @throws Exception
     */
    @Test
    public void testOneConsumer() throws Exception {
        final String DEMO_QUEUE = "queue.demo";
        // 发送消息
        List<String> messages = new ArrayList<>();
        messages.add("锄禾日当午，");
        messages.add("汗滴禾下土，");
        messages.add("谁知盘中餐，");
        messages.add("粒粒皆辛苦。");
        CountDownLatch countDownLatch = new CountDownLatch(messages.size());

        Connection connection = ConnectionHelper.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(DEMO_QUEUE, true, false, false, null);
        for (String message : messages) {
            channel.basicPublish("", DEMO_QUEUE, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            log.info("发送mq消息：{}", message);
        }
        channel.close();
        connection.close();

        // 接收消息
        Connection receiveConnection = ConnectionHelper.getConnection();
        Channel receiveChannel = receiveConnection.createChannel();
        // 设置消费者最多接收未被ack的消息个数
        receiveChannel.basicQos(64);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            log.info("接收mq消息：{}", new String(delivery.getBody()));
            receiveChannel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            countDownLatch.countDown();
        };
        receiveChannel.basicConsume(DEMO_QUEUE, false, deliverCallback, consumerTag -> { });
        countDownLatch.await();
        receiveChannel.close();
        receiveConnection.close();
    }

    /**
     * 测试多个消费者均匀消费
     * @throws Exception
     */
    @Test
    public void testMultiConsumer() throws Exception {
        final String TASK_QUEUE = "queue.task";
        Connection connection = ConnectionHelper.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(TASK_QUEUE, true, false, false, null);
        for (int i = 1; i <= 10; i++) {
            String message = "任务" + i;
            channel.basicPublish("", TASK_QUEUE, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            log.info("发送mq任务：{}", message);
        }
        channel.close();
        connection.close();

        // 多个消费者同时消费一个队列
        CountDownLatch countDownLatch = new CountDownLatch(10);
        List<String> consumers = new ArrayList<>(3);
        consumers.add("consumer1");
        consumers.add("consumer2");
        consumers.add("consumer3");
        for (String consumer : consumers) {
            executor.execute(() -> {
                try {
                    Connection receiveConnection = ConnectionHelper.getConnection();
                    Channel receiveChannel = receiveConnection.createChannel();
                    // 消费者只接收1个消息，确认后再接收下一个
                    receiveChannel.basicQos(1);
                    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                        log.info("{}开始处理：{}", consumer, new String(delivery.getBody()));
                        receiveChannel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                        countDownLatch.countDown();
                    };
                    receiveChannel.basicConsume(TASK_QUEUE, false, deliverCallback, consumerTag -> { });
                    countDownLatch.await();
                    receiveChannel.close();
                    receiveConnection.close();
                } catch (Exception e) {
                    log.error("mq消费出错：", e);
                    throw new BusinessException("mq消费出错：" + e);
                }
            });
        }
        countDownLatch.await();
    }

    /**
     * 测试所有queue都可以接收的fanout消息
     * @throws Exception
     */
    @Test
    public void testFanoutMode() throws Exception {
        final String FANOUT_EXCHANGE_NAME = "fanoutEx";
        List<String> queues = new ArrayList<>(3);
        queues.add("fanoutQueue1");
        queues.add("fanoutQueue2");
        queues.add("fanoutQueue3");

        String message = "公告：全军出击！";
        Connection connection = ConnectionHelper.getConnection();
        Channel channel = connection.createChannel();
        // 定义fanout类型exchange，该exchange会将消息发送到所有绑定的queue
        channel.exchangeDeclare(FANOUT_EXCHANGE_NAME, BuiltinExchangeType.FANOUT.getType(), true, false, null);
        // 定义多个queue
        for (String queue : queues) {
            channel.queueDeclare(queue, true, false, false, null);
            channel.queueBind(queue, FANOUT_EXCHANGE_NAME, "");
        }
        // 发布消息
        channel.basicPublish(FANOUT_EXCHANGE_NAME, "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        log.info("发送mq消息：{}", message);
        channel.close();
        connection.close();

        // 接收消息
        Connection receiveConnection = ConnectionHelper.getConnection();
        Channel receiveChannel = receiveConnection.createChannel();
        for (String queue : queues) {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                log.info("{}接收mq公告：{}", queue, new String(delivery.getBody()));
                receiveChannel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                countDownLatch.countDown();
            };
            receiveChannel.basicConsume(queue, false, deliverCallback, consumerTag -> { });
            countDownLatch.await();
        }
        receiveChannel.close();
        receiveConnection.close();
    }

    /**
     * 测试指定的queue才可以接收的direct消息
     * @throws Exception
     */
    @Test
    public void testDirectMode() throws Exception {
        String DIRECT_EXCHANGE_NAME = "directEx";
        List<QueueVo> queueVos = new ArrayList<>(3);
        QueueVo queueVo1 = new QueueVo("directQueue1", "routingKey1", "", "这是频道1正在播放消息。");
        QueueVo queueVo2 = new QueueVo("directQueue2", "routingKey2", "", "这是频道2正在播放消息。");
        QueueVo queueVo3 = new QueueVo("directQueue3", "routingKey3", "", "这是频道3正在播放消息。");
        queueVos.add(queueVo1);
        queueVos.add(queueVo2);
        queueVos.add(queueVo3);
        for (QueueVo queueVo : queueVos) {
            executor.execute(() -> {
                try {
                    new MqConsumerService().consumeDirectMsg(queueVo.getQueue());
                } catch (Exception e) {
                    log.error("mq消费出错：", e);
                    throw new BusinessException("mq消费出错：" + e);
                }
            });
        }
        new MqProducerService().sendDirectMsg(DIRECT_EXCHANGE_NAME, queueVos);
    }

    /**
     * 测试匹配的queue才可以接收的topic消息
     * @throws Exception
     */
    @Test
    public void testTopicMode() throws Exception {
        String TOPIC_EXCHANGE_NAME = "topicEx";
        List<QueueVo> sendQueues = new ArrayList<>(3);
        QueueVo queueVo1 = new QueueVo("topicQueue1", "topic.to.lucy", "topic.to.lucy.*", "你好呀。");
        QueueVo queueVo2 = new QueueVo("topicQueue2", "topic.to.jean", "topic.to.jean.*", "你好呀。");
        QueueVo queueVo3 = new QueueVo("topicQueue3", "topic.to.kali", "topic.to.kali.*", "你好呀。");
        sendQueues.add(queueVo1);
        sendQueues.add(queueVo2);
        sendQueues.add(queueVo3);
        for (int i = 0; i < sendQueues.size(); i++) {
            QueueVo queueVo = sendQueues.get(i);
            executor.execute(() -> {
                try {
                    new MqConsumerService().consumeTopicMsg(TOPIC_EXCHANGE_NAME, queueVo.getQueue(), queueVo.getConsumerMatchKey());
                } catch (Exception e) {
                    log.error("mq消费出错：", e);
                    throw new BusinessException("mq消费出错：" + e);
                }
            });
        }
        new MqProducerService().sendTopicMsg(TOPIC_EXCHANGE_NAME, sendQueues);
    }
}
