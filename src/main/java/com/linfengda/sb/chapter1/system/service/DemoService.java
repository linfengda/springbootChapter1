package com.linfengda.sb.chapter1.system.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述: 编写一个普通的 Service 来简单构造一个事务场景。
 *
 * @author linfengda
 * @create 2020-01-31 16:57
 */
@Service
public class DemoService {

    @Transactional(rollbackFor = Exception.class)
    public void test1() {
        System.out.println("test1 run...");
        int i = 1 / 0;
        System.out.println("test1 finish...");
    }
}
