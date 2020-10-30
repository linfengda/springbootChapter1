package com.linfengda.sb.chapter1.order.service;

import com.linfengda.sb.chapter1.common.util.SpringUtil;
import com.linfengda.sb.chapter1.order.entity.enums.OrderEvent;
import com.linfengda.sb.chapter1.order.entity.enums.OrderState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

/**
 * @description:
 * @author: linfengda
 * @date: 2020-08-10 15:41
 */
@Slf4j
@Component
public class OrderStateMachineBuilder {

    public void builderOrderStateMachine() throws Exception {
        BeanFactory beanFactory = SpringUtil.getBean(BeanFactory.class);

        StateMachineBuilder.Builder<OrderState, OrderEvent> builder = StateMachineBuilder.builder();

        builder.configureConfiguration()
                .withConfiguration()
                .machineId("orderStateMc")
                .beanFactory(beanFactory);

        builder.configureStates()
                .withStates()
                .initial(OrderState.UN_PAID)
                .states(EnumSet.allOf(OrderState.class));

        builder.configureTransitions()
                .withExternal()
                .source(OrderState.UN_PAID).target(OrderState.WAITING_RECEIVE)
                .event(OrderEvent.PAY)
                .and()
                .withExternal()
                .source(OrderState.WAITING_RECEIVE).target(OrderState.WAITING_CHECK)
                .event(OrderEvent.RECEIVE)
                .and()
                .withChoice()
                .source(OrderState.WAITING_CHECK)
                .first(OrderState.DONE, new OrderStateMachineCheckGuard())
                .last(OrderState.RETURN);
    }

}
