package com.linfengda.sb.chapter1.ctrl;


import com.linfengda.sb.chapter1.bean.req.AcceptOrderReq;
import com.linfengda.sb.chapter1.common.bean.Result;
import com.linfengda.sb.support.redis.lock.annotation.BusinessLock;
import com.linfengda.sb.support.redis.lock.annotation.BusinessLockKey;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 生产大货订单表 前端控制器
 * </p>
 *
 * @author linfengda
 * @since 2021-03-08
 */
@RestController
@RequestMapping("/produceOrder")
public class ProduceOrderCtrl {

    @BusinessLock(prefix = "order", desc = "测试业务锁")
    @GetMapping("/test1")
    public Result test1(@RequestParam @BusinessLockKey Integer orderId) {
        return null;
    }

    @BusinessLock(prefix = "order", desc = "测试业务锁")
    @PostMapping("/test2")
    public Result test2(@RequestBody AcceptOrderReq acceptOrderReq) {
        return null;
    }
}
