package com.linfengda.sb.chapter1.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Optional;

/**
 * @description mq消息成功发送到broker回调
 * @author linfengda
 * @date 2020-10-13 00:53
 */
@Slf4j
public class MyMqConfirmCallback implements RabbitTemplate.ConfirmCallback {


    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String mqMessageId = null;
        if(null != correlationData) {
            mqMessageId = correlationData.getId();
        }
        //correlationId为空说明消息没有落表，只处理错误的情况
        if (null == mqMessageId) {
            if (!ack) {
                log.error("MQ消息发送失败, 交换机确认回调, correlationData:{}, cause:{}", Optional.ofNullable(correlationData).map(CorrelationData::toString).orElse(null), cause);
            }
            return;
        }
        //correlationId不为空说明消息有落表
        if(ack) {
            //更新MQ推送结果
        }else {
            //目前还没做定时扫表重推，打出错误日志
            log.error("MQ消息发送失败, 交换机确认回调, mqMessageIdStr:{}, cause:{}", mqMessageId, cause);
        }
    }
}
