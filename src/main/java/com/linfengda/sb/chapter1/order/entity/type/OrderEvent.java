package com.linfengda.sb.chapter1.order.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 订单事件
 * @author: linfengda
 * @date: 2020-08-10 15:39
 */
@Getter
@AllArgsConstructor
public enum OrderEvent {
    /**
     * 支付
     */
    PAY,
    /**
     * 收货
     */
    RECEIVE,
    /**
     * 验收
     */
    CHECK,
    ;
}
