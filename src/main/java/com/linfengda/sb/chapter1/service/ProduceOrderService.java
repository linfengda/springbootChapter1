package com.linfengda.sb.chapter1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linfengda.sb.chapter1.bean.dto.OrderStateChangeDto;
import com.linfengda.sb.chapter1.bean.entity.ProduceOrder;

/**
 * <p>
 * 生产大货订单表 服务类
 * </p>
 *
 * @author linfengda
 * @since 2021-03-08
 */
public interface ProduceOrderService extends IService<ProduceOrder> {

    /**
     * 更新订单状态
     * @param orderStateChangeDTO  订单状态更新dto
     */
    void changeOrderStatus(OrderStateChangeDto orderStateChangeDTO);
}
