package com.lfd.srv.demo.bean.dto;

import com.lfd.srv.demo.bean.type.OrderEvent;
import lombok.Data;

/**
 * @description 订单状态更新dto
 * @author linfengda
 * @date 2020-11-09 00:19
 */
@Data
public class OrderStateChangeDto {
    private OrderEvent event;
}
