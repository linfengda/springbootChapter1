package com.linfengda.sb.chapter1.demo.lambda;

/**
 * 描述: 定义函数式接口
 *
 * @author linfengda
 * @create 2019-01-24 16:14
 */
@FunctionalInterface
public interface MyFunctionalInterface<T> {

    T doAction(MyCallBack callBack) throws Exception;
}
