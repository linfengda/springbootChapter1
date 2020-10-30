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
     * 待支付
     */
    UN_PAID(1, "待支付"),
    /**
     * 待收货
     */
    WAITING_RECEIVE(2, "待收货"),
    /**
     * 待验收
     */
    WAITING_CHECK(3, "收货"),
    /**
     * 退货
     */
    RETURN(4, "退货"),
    /**
     * 结束
     */
    DONE(5, "结束"),
    ;

    private Integer code;
    private String name;
}
