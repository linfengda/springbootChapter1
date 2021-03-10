package com.lfd.srv.demo.bean.req;

import com.lfd.srv.demo.support.redis.lock.annotation.BusinessLockKey;
import lombok.Data;

/**
 * @description 工厂接单dto
 * @author linfengda
 * @date 2020-12-22 14:37
 */
@Data
public class AcceptOrderReq {
    /**
     * 订单id
     */
    @BusinessLockKey(index = 0)
    private Integer orderId;
    /**
     * 明细id
     */
    @BusinessLockKey(index = 1)
    private Integer detailId;
}
