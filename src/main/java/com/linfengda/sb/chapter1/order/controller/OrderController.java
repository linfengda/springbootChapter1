package com.linfengda.sb.chapter1.order.controller;

import com.linfengda.sb.chapter1.common.entity.Result;
import com.linfengda.sb.chapter1.order.entity.dto.AcceptOrderDTO;
import com.linfengda.sb.support.redis.lock.annotation.BusinessLock;
import com.linfengda.sb.support.redis.lock.annotation.BusinessLockKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 订单控制器
 * @author: linfengda
 * @date: 2020-09-22 15:01
 */
@Slf4j
@RestController()
@RequestMapping("/pc/order")
public class OrderController {

    @BusinessLock(prefix = "order", desc = "测试业务锁")
    @GetMapping("/test1")
    public Result test1(@RequestParam @BusinessLockKey Integer orderId) {
        return null;
    }

    @BusinessLock(prefix = "order", desc = "测试业务锁")
    @PostMapping("/test2")
    public Result test2(@RequestBody AcceptOrderDTO acceptOrderDTO) {
        return null;
    }
}
