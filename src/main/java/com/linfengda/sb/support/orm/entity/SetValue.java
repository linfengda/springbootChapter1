package com.linfengda.sb.support.orm.entity;

import com.linfengda.sb.support.orm.utils.ClassUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 更新参数类
 * @author linfengda
 */
public class SetValue {

    private String partSql = " SET version=version+1,";
    @Getter private List<AttributeValue> params = new ArrayList<>();

    public SetValue add(String fieldName,Object value){
        if (value == null){
            return this;
        }
        partSql += ClassUtil.convert(fieldName)+"=?,";
        params.add(new AttributeValue(value, ClassUtil.convertType(value.getClass())));
        return this;
    }

    public String getPartSql(){
      return  partSql.substring(0,partSql.lastIndexOf(","));
    }
}
