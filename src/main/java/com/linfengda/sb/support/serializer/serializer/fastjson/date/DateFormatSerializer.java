package com.linfengda.sb.support.serializer.serializer.fastjson.date;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.linfengda.sb.support.serializer.util.TimeUtil;

import java.lang.reflect.Type;
import java.sql.Timestamp;

/**
 * @description: 日期输出序列化类
 * @author: linfengda
 * @date: 2020-08-23 23:15
 */
public class DateFormatSerializer implements ObjectSerializer {
    public static final DateFormatMdSerializer INSTANCE = new DateFormatMdSerializer();

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type type, int features){
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeString("");
            return;
        }
        long ms;
        if (object instanceof java.util.Date) {
            ms = ((java.util.Date) object).getTime();
        }else if (object instanceof java.sql.Date) {
            ms = ((java.sql.Date) object).getTime();
        }else if (object instanceof Timestamp) {
            ms = ((Timestamp) object).getTime();
        }else {
            out.writeString("");
            return;
        }
        out.writeString(TimeUtil.format(ms, "yyyy-MM-dd HH:mm:ss"));
    }
}
