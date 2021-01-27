package com.linfengda.sb.chapter1.order.service.impl;

import com.linfengda.sb.chapter1.order.entity.dto.OrderStateChangeDTO;
import com.linfengda.sb.chapter1.order.entity.enums.OrderEvent;
import com.linfengda.sb.chapter1.order.entity.enums.OrderState;
import com.linfengda.sb.chapter1.order.service.OrderService;
import com.linfengda.sb.support.statemachine.GenericStateMachine;
import com.linfengda.sb.support.statemachine.StateMachine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @description
 * @author linfengda
 * @date 2020-11-09 00:20
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {


    @Override
    public void changeOrderStatus(OrderStateChangeDTO orderStateChangeDTO) {
        StateMachine<OrderState, OrderEvent> orderStateMachine = this.buildOrderMachine();
        boolean result = orderStateMachine.sendEvent(orderStateChangeDTO.getEvent());
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
