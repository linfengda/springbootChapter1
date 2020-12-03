package com.linfengda.sb.chapter1.order.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 订单状态
 * @author: linfengda
 * @date: 2020-08-10 15:37
 */
@Getter
@AllArgsConstructor
public enum OrderState {
    /**
     * 待排产
     */
    WAITING_PRODUCE(1, "待排产"),
    /**
     * 待分单
     */
    WAITING_ALLOCATION(2, "待分单"),
    /**
     * 生产中
     */
    PRODUCING(3, "生产中"),
    /**
     * 待发货
     */
    WAITING_DELIVERY(4, "待发货"),
    /**
     * 待收货
     */
    WAITING_RECEIVE(5, "待收货"),
    /**
     * 待验收
     */
    WAITING_CHECK(6, "待验收"),
    /**
     * 退货
     */
    RETURN(7, "退货"),
    /**
     * 结束
     */
    DONE(8, "结束"),
    ;

    private Integer code;
    private String name;
}
