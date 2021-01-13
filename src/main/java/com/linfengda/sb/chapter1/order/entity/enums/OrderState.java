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
     * 待分单
     */
    WAITING_ACCEPT(1, "待分单"),
    /**
     * 生产中
     */
    PRODUCING(2, "生产中"),
    /**
     * 待发货
     */
    WAITING_DELIVERY(3, "待发货"),
    /**
     * 待收货
     */
    WAITING_RECEIVE(4, "待收货"),
    /**
     * 待验收
     */
    WAITING_CHECK(5, "待验收"),
    /**
     * 退货
     */
    RETURN(6, "退货"),
    /**
     * 结束
     */
    DONE(7, "结束"),
    ;

    private Integer code;
    private String name;
}
