package com.lfd.soa.srv.demo.support.statemachine;

import com.lfd.soa.common.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;

/**
 * 状态机实现
 * @author linfengda
 * @date 2020-11-08 23:58
 */
public class GenericStateMachine<S, E> implements StateMachine<S, E> {
    /**
     * 状态机初始状态
     */
    private S initialState;
    /**
     * 状态机下一状态
     */
    private S targetState;
    /**
     * 状态机描述列表
     */
    private List<StateEvent<S, E>> stateEvents = new ArrayList<>();


    @Override
    public StateMachine<S, E> build(S source, S target, E event) {
        StateEvent<S, E> stateEvent = new StateEvent<>(source, target, event);
        stateEvents.add(stateEvent);
        return this;
    }

    @Override
    public StateMachine<S, E> initState(S state) {
        this.initialState = state;
        return this;
    }

    @Override
    public boolean sendEvent(E event) {
        if (null == this.initialState) {
            throw new BusinessException("未初始化状态机状态！");
        }
        if (null == event) {
            throw new BusinessException("触发事件为空！");
        }
        for (StateEvent<S, E> stateEvent : stateEvents) {
            if (event.equals(stateEvent.getEvent())) {
                if (this.initialState == stateEvent.getSource()) {
                    this.targetState = stateEvent.getTarget();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public S getTargetState() {
        return this.targetState;
    }
}
