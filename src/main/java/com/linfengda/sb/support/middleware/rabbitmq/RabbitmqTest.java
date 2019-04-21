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
        //testSimpleMode();
        //testWorkerMode();
        testFanoutMode();

    }

    /**
     * 测试一收一发
     * @throws Exception
     */
    private static void testSimpleMode() throws Exception {
        String DEMO_QUEUE = "demo_queue";
        executor.execute(() -> {
            try {
                new MqConsumerService().consumeSimpleMsg(DEMO_QUEUE);
            } catch (Exception e) {
                log.error("mq消费出错：", e);
                throw new BusinessException("mq消费出错：" + e);
            }
        });
        new MqProducerService().sendSimpleMsg(DEMO_QUEUE);
    }

    /**
     * 测试多个worker均匀的处理任务
     * @throws Exception
     */
    private static void testWorkerMode() throws Exception {
        String TASK_QUEUE_NAME = "task_queue";
        for (int i = 0; i < 3; i++) {
            String consumer = "consumer" + i;
            executor.execute(() -> {
                try {
                    new MqConsumerService().consumeWorkerMsg(TASK_QUEUE_NAME, consumer);
                } catch (Exception e) {
                    log.error("mq消费出错：", e);
                    throw new BusinessException("mq消费出错：" + e);
                }
            });
        }
        new MqProducerService().sendWorkerMsg(TASK_QUEUE_NAME);
    }

    /**
     * 测试所有绑定到exchange的queue都可以接收消息
     * @throws Exception
     */
    private static void testFanoutMode() throws Exception {
        String FANOUT_EXCHANGE_NAME = "fanoutEx";
        List<String> queues = new ArrayList<>(3);
        queues.add("fanoutQueue1");
        queues.add("fanoutQueue2");
        queues.add("fanoutQueue3");
        executor.execute(() -> {
            try {
                new MqConsumerService().consumeFanoutMsg(queues);
            } catch (Exception e) {
                log.error("mq消费出错：", e);
                throw new BusinessException("mq消费出错：" + e);
            }
        });
        new MqProducerService().sendFanoutMsg(FANOUT_EXCHANGE_NAME, queues);
    }
}
