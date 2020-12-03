package com.linfengda.sb.support.orm.entity;

import com.linfengda.sb.support.orm.utils.ClassUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 更新参数类
 *
 * @author linfengda
 */
public class SetValue {
    private String updateSql = " SET version=version+1,";
    private List<AttributeValue> params = new ArrayList<>();

    public SetValue add(String fieldName, Object value) {
        if (value == null) {
            return this;
        }
        updateSql += ClassUtil.convert(fieldName) + "=?,";
        params.add(new AttributeValue(value, ClassUtil.convertType(value.getClass())));
        return this;
    }

    public String getUpdateSql() {
        return updateSql.substring(0, updateSql.lastIndexOf(","));
    }

    public List<AttributeValue> getParams() {
        return params;
    }
}
