package com.linfengda.sb.chapter1.order.entity.dto;

import com.linfengda.sb.chapter1.order.entity.enums.OrderEvent;
import lombok.Data;

/**
 * @description: 订单状态更新dto
 * @author: linfengda
 * @date: 2020-11-09 00:19
 */
@Data
public class OrderStateChangeDTO {
    private OrderEvent event;
}
