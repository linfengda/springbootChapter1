package com.linfengda.sb.chapter1.order.service;

import com.linfengda.sb.chapter1.order.entity.type.OrderEvent;
import com.linfengda.sb.chapter1.order.entity.type.OrderState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;


/**
 * @description:
 * @author: linfengda
 * @date: 2020-08-10 16:05
 */
public class OrderStateMachineCheckGuard implements Guard<OrderState, OrderEvent> {

    @Override
    public boolean evaluate(StateContext<OrderState, OrderEvent> context) {
        return true;
    }
}
