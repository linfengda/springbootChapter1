package com.linfengda.sb.support.serializable.serializer.fastjson.date;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.linfengda.sb.chapter1.common.util.TimeUtil;

import java.lang.reflect.Type;
import java.sql.Timestamp;

/**
 * @description: 日期输出序列化类
 * @author: linfengda
 * @date: 2020-08-22 18:51
 */
public class DateFormatMdSerializer implements ObjectSerializer {
    private static final String NOW_YEAR_PATTERN = "yyyy";
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
        if (TimeUtil.format(ms, NOW_YEAR_PATTERN).equals(TimeUtil.format(System.currentTimeMillis(), NOW_YEAR_PATTERN))){
            out.writeString(TimeUtil.format(ms, "MM-dd"));
        }else {
            out.writeString(TimeUtil.format(ms, "yyyy-MM-dd"));
        }
    }
}
