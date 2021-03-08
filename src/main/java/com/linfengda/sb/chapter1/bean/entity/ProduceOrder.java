package com.linfengda.sb.chapter1.bean.entity;

import com.linfengda.sb.chapter1.common.bean.po.BaseIncrementEntity;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 生产大货订单表
 * </p>
 *
 * @author linfengda
 * @since 2021-03-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ProduceOrder对象", description="生产大货订单表")
public class ProduceOrder extends BaseIncrementEntity<ProduceOrder> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "生产状态")
    private Integer state;

    @ApiModelProperty(value = "订单号")
    private String orderNumber;

    @ApiModelProperty(value = "sku")
    private String sku;

    @ApiModelProperty(value = "参考sku")
    private String referenceSku;

    @ApiModelProperty(value = "生产单价")
    private BigDecimal purchasePrice;

    @ApiModelProperty(value = "参考图片地址")
    private String referenceImageUrl;

    @ApiModelProperty(value = "面料品类")
    private Integer materialTypeEnum;

    @ApiModelProperty(value = "三级分类")
    private Integer threeCategoryId;

    @ApiModelProperty(value = "0：无特殊工艺，1：特殊工艺，")
    private Boolean specialTechnologyTag;

    @ApiModelProperty(value = "特殊工艺描述")
    private String specialTechnologyText;

    @ApiModelProperty(value = "是否首单，0：否，1：是")
    private Integer firstOrder;

    @ApiModelProperty(value = "是否紧急，0：否，1：是")
    private Integer urgent;

    @ApiModelProperty(value = "供应商")
    private String supplier;

    @ApiModelProperty(value = "供应商id")
    private Integer supplierId;

    @ApiModelProperty(value = "跟单员")
    private String merchandiser;

    @ApiModelProperty(value = "接单时间")
    private LocalDateTime acceptTime;

    @ApiModelProperty(value = "裁剪时间")
    private LocalDateTime cutTime;

    @ApiModelProperty(value = "车缝时间")
    private LocalDateTime sewTime;

    @ApiModelProperty(value = "后整时间")
    private LocalDateTime sortTime;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime lastUpdateTime;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
