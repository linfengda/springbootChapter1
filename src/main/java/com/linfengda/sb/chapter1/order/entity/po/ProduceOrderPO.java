package com.linfengda.sb.chapter1.order.entity.po;

import com.linfengda.sb.chapter1.common.entity.po.BasePO;
import com.linfengda.sb.support.orm.annontation.Id;
import com.linfengda.sb.support.orm.annontation.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @description: 生产订单po
 * @author: linfengda
 * @date: 2020-10-14 23:21
 */
@Table(name = "produce_order")
@Data
public class ProduceOrderPO extends BasePO {
    /**
     * 主键id
     */
    @Id
    private Integer id;
    /**
     * 生产状态{@link com.linfengda.sb.chapter1.order.entity.enums.OrderState}
     */
    private Integer state;
    /**
     * 订单号
     */
    private String orderNumber;
    /**
     * sku
     */
    private String sku;
    /**
     * 参考sku
     */
    private String referenceSku;
    /**
     * 生产单价
     */
    private BigDecimal purchasePrice;
    /**
     * 参考图片地址
     */
    private String referenceImage;
    /**
     * 面料品类
     */
    private Integer materialTypeEnum;
    /**
     * 三级分类
     */
    private String threeCategoryId;
    /**
     * 0：无特殊工艺，1：特殊工艺
     */
    private Integer specialTechnologyTag;
    /**
     * 特殊工艺名称
     */
    private String specialTechnologyText;
    /**
     * 是否首单，0：否，1：是
     */
    private Integer firstOrder;
    /**
     * 是否紧急，0：否，1：是
     */
    private Integer urgent;
    /**
     * 供应商
     */
    private String supplier;
    /**
     * 供应商id
     */
    private Integer supplierId;
    /**
     * 跟单员
     */
    private String merchandiser;
    /**
     * 事业部
     */
    private String groupName;
    /**
     * 事业部id
     */
    private Integer groupId;
    /**
     * 接单时间
     */
    private Timestamp acceptTime;
}
