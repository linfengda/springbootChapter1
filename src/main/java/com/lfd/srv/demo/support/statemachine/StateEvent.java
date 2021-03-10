package com.lfd.srv.demo.support.statemachine;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 事件触发状态改变抽象
 * @author linfengda
 * @date 2020-11-09 00:03
 */
@Getter
@AllArgsConstructor
public class StateEvent<S, E> {
    /**
     * 开始状态
     */
    private S source;
    /**
     * 结束状态
     */
    private S target;
    /**
     * 触发事件
     */
    private E event;
}
