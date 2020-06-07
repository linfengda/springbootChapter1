package com.linfengda.sb.chapter1.common.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.lang.reflect.Type;

/**
 * @Description:
 * @Author: liugenhua
 * @Date created in 2018/5/3 14:50
 */
public class LongToStringSerializer implements ObjectSerializer {
    public static final LongToStringSerializer INSTANCE = new LongToStringSerializer();

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeString("");
            return;
        }else {
            String strVal = object.toString();
            out.writeString(strVal);
        }
    }
}
