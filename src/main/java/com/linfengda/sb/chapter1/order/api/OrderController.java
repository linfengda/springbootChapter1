package com.linfengda.sb.chapter1.order.api;

import com.linfengda.sb.chapter1.common.entity.Result;
import com.linfengda.sb.chapter1.order.entity.dto.BigBomMaterialQueryDTO;
import com.linfengda.sb.support.apivalidator.annotation.ApiValidator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 订单控制器
 * @author: linfengda
 * @date: 2020-09-22 15:01
 */
@RestController()
@RequestMapping("/pc/order")
public class OrderController {

    @PostMapping("/getOrderBigBomMaterials")
    public Result getOrderBigBomMaterials(@ApiValidator @RequestBody BigBomMaterialQueryDTO bigBomMaterialQueryDTO) throws Exception {
        return new Result("请求成功");
    }
}
