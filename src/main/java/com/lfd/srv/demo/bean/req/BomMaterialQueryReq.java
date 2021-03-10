package com.lfd.srv.demo.bean.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description 大货物料查询DTO
 * @author linfengda
 * @date 2020-09-22 15:03
 */
@Data
public class BomMaterialQueryReq {
    /**
     * sku
     */
    @NotBlank(message = "sku不能为空")
    private String sku;
    /**
     * 版本
     */
    @NotNull(message = "版本不能为空")
    private Integer version;
}
