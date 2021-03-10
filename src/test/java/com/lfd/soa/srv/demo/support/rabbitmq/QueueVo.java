package com.lfd.soa.srv.demo.support.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 描述:
 *
 * @author linfengda
 * @create 2019-04-21 20:39
 */
@Data
@AllArgsConstructor
public class QueueVo {
    private String queue;
    private String routingKey;
    private String consumerMatchKey;
    private String message;
}
