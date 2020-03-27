package com.linfengda.sb.chapter1.common.lambda;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 描述: 测试lambda
 *
 * @author linfengda
 * @create 2019-01-24 16:07
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class LambdaTest {

    public void lambadaAction(MyFunctionalInterface<Object> action) throws Exception {
        MyCallBack myCallBack = new MyCallBack();
        myCallBack.setMySelf("Jack");
        myCallBack.setMySon("Jack son");
        action.doAction(myCallBack);
    }

    @Test
    public void doTest() throws Exception {
        LambdaTest lambdaTest = new LambdaTest();
        // lambda表达式方法体其实就是函数式接口的实现
        lambdaTest.lambadaAction(callBack -> callBack.callMyself());
        lambdaTest.lambadaAction(callBack -> callBack.callMyson());
    }

}
