package com.linfengda.sb.chapter1.order.api;

import com.alibaba.fastjson.JSON;
import com.linfengda.sb.chapter1.common.entity.Result;
import com.linfengda.sb.chapter1.order.entity.dto.BigBomMaterialQueryDTO;
import com.linfengda.sb.support.apivalidator.annotation.ApiValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @description: 模拟plm系统对外api
 * @author: linfengda
 * @date: 2020-09-28 00:08
 */
@Slf4j
@RestController()
@RequestMapping("/pc/plm")
public class PlmOuterControlller {

    @PostMapping("/getOrderBigBomMaterials")
    public Result getOrderBigBomMaterials(@ApiValidator @RequestBody BigBomMaterialQueryDTO bigBomMaterialQueryDTO) throws Exception {
        log.info("查询订单大货物料信息，bigBomMaterialQueryDTO={}", JSON.toJSONString(bigBomMaterialQueryDTO));
        return new Result("请求成功");
    }

    @GetMapping("/getMaterialPrice")
    public Result getOrderInfoById(@NotBlank String sku) throws Exception {
        log.info("查询订单信息，sku={}", sku);
        return new Result("请求成功");
    }
}
