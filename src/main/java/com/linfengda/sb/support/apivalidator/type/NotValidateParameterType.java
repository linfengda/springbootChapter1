package com.linfengda.sb.support.apivalidator.type;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * 描述: 不支持校验的参数类型
 *
 * @author linfengda
 * @create 2019-12-23 15:38
 */
@Getter
@AllArgsConstructor
public enum NotValidateParameterType {
    /**
     * Map
     */
    MAP(Map.class.getName()),
    /**
     * JSONObject
     */
    JSON(JSONObject.class.getName()),
    /**
     * List
     */
    LIST(List.class.getName());

    private String type;

    public static boolean isNotValidateParameterType(String type){
        for (NotValidateParameterType value : values()) {
            if (value.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
