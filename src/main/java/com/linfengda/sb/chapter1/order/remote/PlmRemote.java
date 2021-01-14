package com.linfengda.sb.chapter1.order.remote;

import com.alibaba.fastjson.JSONObject;
import com.linfengda.sb.chapter1.order.entity.dto.BigBomMaterialQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description:
 * @author: linfengda
 * @date: 2020-09-22 14:57
 */
@FeignClient(name = "plm-service", url = "http://localhost:9000", configuration = {AppKeyInterceptor.class})
public interface PlmRemote {

    /**
     * 请求plm订单大货物料信息
     * @param bigBomMaterialQueryDTO
     * @return
     */
    @PostMapping("/pc/plm/outer/getOrderBigBomMaterials")
    JSONObject getOrderBigBomMaterials(@RequestBody BigBomMaterialQueryDTO bigBomMaterialQueryDTO);

    /**
     * 请求plm物料单价
     * @param sku
     * @return
     */
    @GetMapping("/pc/plm/outer/getMaterialPrice")
    JSONObject getMaterialPrice(@RequestParam String sku);
}
