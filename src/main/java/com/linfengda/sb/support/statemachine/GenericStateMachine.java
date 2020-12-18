package com.linfengda.sb.support.statemachine;

import com.linfengda.sb.chapter1.common.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 状态机实现
 * @author: linfengda
 * @date: 2020-11-08 23:58
 */
public class GenericStateMachine<S, E> implements StateMachine<S, E> {
    /**
     * 状态机初始状态
     */
    private S initialState;
    /**
     * 状态机描述列表
     */
    private List<StateEvent<S, E>> stateEvents = new ArrayList<>();


    @Override
    public StateMachine<S, E> build(S source, S target, E event) {
        StateEvent<S, E> stateEvent = new StateEvent(source, target, event);
        stateEvents.add(stateEvent);
        return this;
    }

    @Override
    public void initState(S state) {
        this.initialState = state;
    }

    @Override
    public boolean sendEvent(E event) {
        if (null == event) {
            throw new BusinessException("触发事件为空！");
        }
        if (null == this.initialState) {
            throw new BusinessException("未初始化状态机状态！");
        }
        for (StateEvent<S, E> stateEvent : stateEvents) {
            if (event.equals(stateEvent.getEvent())) {
                if (this.initialState == stateEvent.getSource()) {
                    return true;
                }
            }
        }
        return false;
    }
}
