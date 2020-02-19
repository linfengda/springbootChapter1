package com.linfengda.sb.chapter1.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述: 编写一个普通的 Service 来简单构造一个事务场景。
 *
 * @author linfengda
 * @create 2020-02-03 16:12
 */
@Service
public class DemoService2 {
    @Autowired
    private DemoService demoService;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void test2() {
        System.out.println("test2 run...");
        demoService.test1();
        System.out.println("test2 finish...");
    }
}
