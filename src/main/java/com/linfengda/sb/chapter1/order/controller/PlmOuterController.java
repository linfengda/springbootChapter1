package com.linfengda.sb.chapter1.order.controller;

import com.alibaba.fastjson.JSON;
import com.linfengda.sb.chapter1.common.entity.Result;
import com.linfengda.sb.chapter1.order.entity.dto.BigBomMaterialQueryDTO;
import com.linfengda.sb.support.apivalidator.annotation.ApiValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * @description 模拟plm系统对外api
 * @author linfengda
 * @date 2020-09-28 00:08
 */
@Slf4j
@RestController()
@RequestMapping("/pc/plm/outer")
public class PlmOuterController {

    @PostMapping("/getOrderBigBomMaterials")
    public Result getOrderBigBomMaterials(@ApiValidator BigBomMaterialQueryDTO bigBomMaterialQueryDTO) {
        log.info("查询plm系统订单大货物料信息，bigBomMaterialQueryDTO={}", JSON.toJSONString(bigBomMaterialQueryDTO));
        return new Result("请求成功");
    }

    @GetMapping("/getMaterialPrice")
    public Result getMaterialPrice(@NotBlank String sku) {
        log.info("查询plm系统物料单价信息，sku={}", sku);
        return new Result("请求成功");
    }
}
