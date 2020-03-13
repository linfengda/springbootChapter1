package com.linfengda.sb.chapter1.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 描述: 编写一个Service来简单构造IOC场景
 *
 * @author linfengda
 * @create 2020-02-03 16:12
 */
@Service
public class DemoService2 {
    @Resource
    private DemoService demoService;

    @Transactional(rollbackFor = Exception.class)
    public void test2() {
        System.out.println("test2 run...");
        demoService.test1();
    }
}
