package com.linfengda.sb.chapter1.order.entity.po;

import com.linfengda.sb.support.orm.tableAnnontation.Id;
import com.linfengda.sb.support.orm.tableAnnontation.Table;
import lombok.Data;

/**
 * @description: 订单po
 * @author: linfengda
 * @date: 2020-10-14 23:21
 */
@Table(name = "order_record")
@Data
public class OrderRecordPO {
    /**
     * 主键id
     */
    @Id
    private Integer id;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 部门别名
     */
    private String departmentAliasName;
    /**
     * 部门类型{@link com.linfengda.sb.chapter1.system.entity.type.SysDepartmentType}
     */
    private Integer type;
}
