package com.linfengda.sb.chapter1.lambdaTest;

/**
 * 描述: 定义函数式接口
 *
 * @author linfengda
 * @create 2019-01-24 16:14
 */
@FunctionalInterface
public interface MyFunctionalInterface<T> {

    T doSomeThing(MyCallBack callBack) throws Exception;
}
