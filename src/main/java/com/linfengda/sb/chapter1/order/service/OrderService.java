package com.linfengda.sb.chapter1.order.service;

import com.linfengda.sb.chapter1.order.entity.dto.OrderStateChangeDTO;

/**
 * @description: 订单服务
 * @author: linfengda
 * @date: 2020-11-09 00:18
 */
public interface OrderService {

    /**
     * 更新订单状态
     * @param orderStateChangeDTO  订单状态更新dto
     */
    void changeOrderStatus(OrderStateChangeDTO orderStateChangeDTO);
}
