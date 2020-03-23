package com.linfengda.sb.chapter1.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述: 编写一个Service来简单构造IOC场景
 *
 * @author linfengda
 * @create 2020-01-31 16:57
 */
@Service
public class DemoService {

    public void test1() {
        System.out.println("test1 run...");
    }
}
