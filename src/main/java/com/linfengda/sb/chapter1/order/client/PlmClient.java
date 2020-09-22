package com.linfengda.sb.chapter1.order.client;

import com.alibaba.fastjson.JSONObject;
import com.linfengda.sb.chapter1.order.entity.dto.BigBomMaterialQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @description:
 * @author: linfengda
 * @date: 2020-09-22 14:57
 */
@FeignClient(name = "plm-service", url = "http://localhost:9000")
public interface PlmClient {

    /**
     * 请求plm订单大货物料信息
     * @param bigBomMaterialQueryDTO
     * @return
     */
    @PostMapping("/pc/order/getOrderBigBomMaterials")
    JSONObject getOrderBigBomMaterials(@RequestBody BigBomMaterialQueryDTO bigBomMaterialQueryDTO);
}
