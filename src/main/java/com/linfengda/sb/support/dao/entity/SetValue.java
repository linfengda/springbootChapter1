package com.linfengda.sb.support.dao.entity;

import com.linfengda.sb.support.dao.utils.ClassUtil;
import com.linfengda.sb.support.dao.utils.NameUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: liugenhua
 * @Date created in 2018/4/27 10:42
 */
public class SetValue {

    private String partSql = " SET version=version+1,";
    @Getter private List<AttributeValue> params = new ArrayList<>();

    public SetValue add(String fieldName,Object value){
        if (value == null){
            return this;
        }
        partSql += NameUtil.convert(fieldName)+"=?,";
        params.add(new AttributeValue(value, ClassUtil.convertType(value.getClass())));
        return this;
    }

    public String getPartSql(){
      return  partSql.substring(0,partSql.lastIndexOf(","));
    }
}
