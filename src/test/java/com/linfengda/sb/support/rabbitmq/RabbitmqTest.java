package com.linfengda.sb.support.rabbitmq;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.chapter1.common.thread.ThreadPoolHelper;
import com.linfengda.sb.support.rabbitmq.service.MqConsumerService;
import com.linfengda.sb.support.rabbitmq.service.MqProducerService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;

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
        Connection connection = ConnectionHelper.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(DEMO_QUEUE, true, false, false, null);
        for (String message : messages) {
            channel.basicPublish("", DEMO_QUEUE, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            log.info("发送mq消息：{}", message);
        }
        channel.close();
        connection.close();
        // 消费消息
        new MqConsumerService().consumeMsgOne(DEMO_QUEUE);
    }

    /**
     * 测试多个消费者均匀消费
     * @throws Exception
     */
    private static void testMultiConsumer() throws Exception {
        String TASK_QUEUE_NAME = "queue.task";
        List<String> consumers = new ArrayList<>(3);
        consumers.add("consumer1");
        consumers.add("consumer2");
        consumers.add("consumer3");
        for (String consumer : consumers) {
            executor.execute(() -> {
                try {
                    new MqConsumerService().consumeMsgMulti(TASK_QUEUE_NAME, consumer);
                } catch (Exception e) {
                    log.error("mq消费出错：", e);
                    throw new BusinessException("mq消费出错：" + e);
                }
            });
        }
        new MqProducerService().sendWorkerMsg(TASK_QUEUE_NAME);
    }

    /**
     * 测试所有queue都可以接收的fanout消息
     * @throws Exception
     */
    private static void testFanoutMode() throws Exception {
        String FANOUT_EXCHANGE_NAME = "fanoutEx";
        List<String> queues = new ArrayList<>(3);
        queues.add("fanoutQueue1");
        queues.add("fanoutQueue2");
        queues.add("fanoutQueue3");
        for (String queue : queues) {
            executor.execute(() -> {
                try {
                    new MqConsumerService().consumeFanoutMsg(queue);
                } catch (Exception e) {
                    log.error("mq消费出错：", e);
                    throw new BusinessException("mq消费出错：" + e);
                }
            });
        }
        new MqProducerService().sendFanoutMsg(FANOUT_EXCHANGE_NAME, queues);
    }

    /**
     * 测试指定的queue才可以接收的direct消息
     * @throws Exception
     */
    private static void testDirectMode() throws Exception {
        String DIRECT_EXCHANGE_NAME = "directEx";
        List<QueueVo> queueVos = new ArrayList<>(3);
        QueueVo queueVo1 = new QueueVo("directQueue1", "routingKey1", "我不是渣，我只是想给每个女孩子一个家。");
        QueueVo queueVo2 = new QueueVo("directQueue2", "routingKey2", "我从没有绿过任何人，我只是忘记了说分手。");
        QueueVo queueVo3 = new QueueVo("directQueue3", "routingKey3", "你的多情出卖我的爱情。");
        queueVos.add(queueVo1);
        queueVos.add(queueVo2);
        queueVos.add(queueVo3);
        for (QueueVo queueVo : queueVos) {
            executor.execute(() -> {
                try {
                    new MqConsumerService().consumeDirectMsg(queueVo);
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
    private static void testTopicMode() throws Exception {
        String TOPIC_EXCHANGE_NAME = "topicEx";
        List<String> matchKeys = new ArrayList<>(3);
        matchKeys.add("topic.to.lucy.*");
        matchKeys.add("topic.to.jean.*");
        matchKeys.add("topic.to.kali.*");
        List<QueueVo> sendQueues = new ArrayList<>(3);
        QueueVo queueVo1 = new QueueVo("topicQueue1", "topic.to.lucy", "你好呀。");
        QueueVo queueVo2 = new QueueVo("topicQueue2", "topic.to.jean", "你好呀。");
        QueueVo queueVo3 = new QueueVo("topicQueue3", "topic.to.kali", "你好呀。");
        sendQueues.add(queueVo1);
        sendQueues.add(queueVo2);
        sendQueues.add(queueVo3);
        for (int i = 0; i < sendQueues.size(); i++) {
            QueueVo queueVo = sendQueues.get(i);
            String matchKey = matchKeys.get(i);
            executor.execute(() -> {
                try {
                    new MqConsumerService().consumeTopicMsg(TOPIC_EXCHANGE_NAME, queueVo.getQueue(), matchKey);
                } catch (Exception e) {
                    log.error("mq消费出错：", e);
                    throw new BusinessException("mq消费出错：" + e);
                }
            });
        }
        new MqProducerService().sendTopicMsg(TOPIC_EXCHANGE_NAME, sendQueues);
    }
}
