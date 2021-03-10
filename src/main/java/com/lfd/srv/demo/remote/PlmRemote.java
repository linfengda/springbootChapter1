package com.lfd.srv.demo.remote;

import com.alibaba.fastjson.JSONObject;
import com.lfd.srv.demo.bean.req.BomMaterialQueryReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description
 * @author linfengda
 * @date 2020-09-22 14:57
 */
@FeignClient(name = "plm-service", url = "http://localhost:9000", configuration = {AppKeyInterceptor.class})
public interface PlmRemote {

    /**
     * 请求plm订单大货物料信息
     * @param bomMaterialQueryReq
     * @return
     */
    @PostMapping("/pc/plm/outer/getOrderBigBomMaterials")
    JSONObject getOrderBigBomMaterials(@RequestBody BomMaterialQueryReq bomMaterialQueryReq);

    /**
     * 请求plm物料单价
     * @param sku
     * @return
     */
    @GetMapping("/pc/plm/outer/getMaterialPrice")
    JSONObject getMaterialPrice(@RequestParam String sku);
}
