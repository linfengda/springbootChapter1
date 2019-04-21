package com.linfengda.sb.support.middleware.rabbitmq;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.support.lettuce.helper.ThreadPoolHelper;
import com.linfengda.sb.support.middleware.rabbitmq.service.MqConsumerService;
import com.linfengda.sb.support.middleware.rabbitmq.service.MqProducerService;
import lombok.extern.slf4j.Slf4j;
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
public class RabbitmqTest {
    private static ThreadPoolTaskExecutor executor = ThreadPoolHelper.initThreadPool(10, 50);

    public static void main(String[] argv) throws Exception {
        testSimpleMode();
        //testWorkerMode();


    }

    /**
     * 测试简单的发送接收
     * @throws Exception
     */
    private static void testSimpleMode() throws Exception {
        final String DEMO_QUEUE = "demo_queue";
        // 消费消息
        executor.execute(() -> {
            try {
                new MqConsumerService().consumeSimpleMsg(DEMO_QUEUE);
            } catch (Exception e) {
                log.error("mq消费出错：", e);
                throw new BusinessException("mq消费出错：" + e);
            }
        });
        // 发送消息
        new MqProducerService().sendSimpleMsg(DEMO_QUEUE);
    }

    /**
     * 测试worker模式
     * @throws Exception
     */
    private static void testWorkerMode() throws Exception {
        final String TASK_QUEUE_NAME = "task_queue";
        // 消费消息
        for (int i = 0; i < 3; i++) {
            String consumer = "consumer" + i;
            executor.execute(() -> {
                try {
                    new MqConsumerService().consumeWorkerMsg(TASK_QUEUE_NAME, consumer);
                } catch (Exception e) {
                    log.error("consumer1消费出错：", e);
                    throw new BusinessException("consumer1消费出错：" + e);
                }
            });
        }
        // 发送消息
        new MqProducerService().sendWorkerMsg(TASK_QUEUE_NAME);
    }


}
