package com.linfengda.sb.chapter1.bean.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description 订单事件
 * @author linfengda
 * @date 2020-08-10 15:39
 */
@Getter
@AllArgsConstructor
public enum OrderEvent {
    /**
     * 工厂接单
     */
    ACCEPT_ORDER,
    /**
     * 工厂已打包
     */
    PACKAGE,
    /**
     * 工厂已发货
     */
    SEND,
    /**
     * 仓库已收货
     */
    RECEIVE,
    /**
     * 质检不合格
     */
    QC_NO_PASS,
    /**
     * 质检合格
     */
    QC_PASS,
    ;
}
