package com.lfd.soa.srv.demo.demo.function;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 描述: 测试函数式接口
 *
 * @author linfengda
 * @create 2019-01-24 16:07
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class FunctionInterfaceTest {

    public void doFunction(MyFunctionalInterface<Object> action) throws Exception {
        myCallBackService myCallBackService = new myCallBackService();
        action.doAction(myCallBackService);
    }

    @Test
    public void doTest() throws Exception {
        FunctionInterfaceTest functionInterfaceTest = new FunctionInterfaceTest();
        // lambda表达式方法体其实就是函数式接口的实现
        functionInterfaceTest.doFunction(callBack -> callBack.callMyself());
        functionInterfaceTest.doFunction(callBack -> callBack.callMySon());
    }

}
