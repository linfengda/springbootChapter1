package com.linfengda.sb.support.rabbitmq;

import lombok.Data;

/**
 * 描述:
 *
 * @author linfengda
 * @create 2019-04-21 20:39
 */
@Data
public class QueueVo {
    private String queue;
    private String routingKey;
    private String message;

    public QueueVo(String queue, String routingKey, String message) {
        this.queue = queue;
        this.routingKey = routingKey;
        this.message = message;
    }
}
