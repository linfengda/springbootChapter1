package com.lfd.soa.srv.demo.demo.function;

import lombok.extern.slf4j.Slf4j;

/**
 * 描述: 定义回调函数
 *
 * @author linfengda
 * @create 2019-01-24 16:14
 */
@Slf4j
public class myCallBackService {

    boolean callMyself() {
        log.info("---------------------------------call myself");
        return true;
    }

    boolean callMySon() {
        log.info("---------------------------------call mySon");
        return true;
    }

}
