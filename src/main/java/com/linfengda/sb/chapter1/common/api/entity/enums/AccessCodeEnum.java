package com.linfengda.sb.chapter1.common.api.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 访问权限code类型
 * @author: linfengda
 * @date: 2020-09-19 17:08
 */
@Getter
@AllArgsConstructor
public enum AccessCodeEnum {
    /**
     * 管理员权限
     */
    ADMIN("admin", "管理员权限"),
    /**
     * 供应商生产文员权限
     */
    SUPPLIER_PRODUCT_MANAGER("supplierProductManager", "供应商生产文员权限"),
    /**
     * 供应商财务文员权限
     */
    SUPPLIER_PRODUCT_BILL_MANAGER("supplierProductBillManager", "供应商财务文员权限"),
    ;
    /**
     * 权限code
     */
    private String accessCode;
    /**
     * 权限名称
     */
    private String accessName;

    private static Map<String, AccessCodeEnum> accessCodeEnumMap;
    static {
        accessCodeEnumMap = new HashMap<>(8);
        for (AccessCodeEnum value : values()) {
            accessCodeEnumMap.put(value.getAccessCode(), value);
        }
    }

    public static AccessCodeEnum getAccessCodeEnumByMenuId(String accessCode){
        return accessCodeEnumMap.get(accessCode);
    } 
}
