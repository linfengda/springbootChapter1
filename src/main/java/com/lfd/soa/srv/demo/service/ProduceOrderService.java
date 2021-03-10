package com.lfd.soa.srv.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lfd.soa.srv.demo.bean.entity.ProduceOrder;
import com.lfd.soa.srv.demo.bean.dto.OrderStateChangeDto;

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
