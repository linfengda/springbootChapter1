package com.linfengda.sb.chapter1.order.api;

import com.linfengda.sb.chapter1.common.entity.Result;
import com.linfengda.sb.support.redis.lock.annotation.RequestLock;
import com.linfengda.sb.support.redis.lock.annotation.RequestLockKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 订单控制器
 * @author: linfengda
 * @date: 2020-09-22 15:01
 */
@Slf4j
@RestController()
@RequestMapping("/pc/order")
public class OrderController {

    @RequestLock(desc = "更新订单状态业务锁")
    @GetMapping("/test")
    public Result test(@RequestParam @RequestLockKey(nullable = true) Integer orderId) {
        return null;
    }

}
