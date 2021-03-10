package com.lfd.soa.srv.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lfd.soa.srv.demo.bean.entity.ProduceOrder;
import com.lfd.soa.srv.demo.mapper.ProduceOrderMapper;
import com.lfd.soa.srv.demo.service.ProduceOrderService;
import com.lfd.soa.srv.demo.bean.dto.OrderStateChangeDto;
import com.lfd.soa.srv.demo.bean.type.OrderEvent;
import com.lfd.soa.srv.demo.bean.type.OrderState;
import com.lfd.soa.srv.demo.support.statemachine.GenericStateMachine;
import com.lfd.soa.srv.demo.support.statemachine.StateMachine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 生产大货订单表 服务实现类
 * </p>
 *
 * @author linfengda
 * @since 2021-03-08
 */
@Slf4j
@Service
public class ProduceOrderServiceImpl extends ServiceImpl<ProduceOrderMapper, ProduceOrder> implements ProduceOrderService {

    @Override
    public void changeOrderStatus(OrderStateChangeDto orderStateChangeDto) {
        StateMachine<OrderState, OrderEvent> orderStateMachine = this.buildOrderMachine();
        boolean result = orderStateMachine.sendEvent(orderStateChangeDto.getEvent());
        if (result) {
            log.info("状态机校验通过，允许订单状态更新！");
        }else {
            log.info("状态机校验未通过，不允许订单状态更新！");
        }
    }

    private StateMachine<OrderState, OrderEvent> buildOrderMachine() {
        StateMachine<OrderState, OrderEvent> orderStateMachine = new GenericStateMachine<>();
        orderStateMachine
                .initState(OrderState.WAITING_ACCEPT)
                .build(OrderState.WAITING_ACCEPT, OrderState.PRODUCING, OrderEvent.ACCEPT_ORDER)
                .build(OrderState.PRODUCING, OrderState.WAITING_DELIVERY, OrderEvent.PACKAGE)
                .build(OrderState.WAITING_DELIVERY, OrderState.WAITING_RECEIVE, OrderEvent.SEND)
                .build(OrderState.WAITING_RECEIVE, OrderState.WAITING_CHECK, OrderEvent.RECEIVE)
                .build(OrderState.WAITING_CHECK, OrderState.RETURN, OrderEvent.QC_NO_PASS)
                .build(OrderState.WAITING_CHECK, OrderState.DONE, OrderEvent.QC_PASS)
        ;
        return orderStateMachine;
    }
}
