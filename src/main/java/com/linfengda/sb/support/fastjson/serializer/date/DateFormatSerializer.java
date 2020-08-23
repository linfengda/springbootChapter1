package com.linfengda.sb.support.fastjson.serializer.date;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.linfengda.sb.chapter1.common.util.TimeUtil;

import java.lang.reflect.Type;

/**
 * @description: 日期输出序列化类
 * @author: linfengda
 * @date: 2020-08-22 18:51
 */
public class DateFormatSerializer extends AbstractDateBaseSerializer implements ObjectSerializer {
    private static final String NOW_YEAR_PATTERN = "yyyy";
    public static final DateFormatSerializer INSTANCE = new DateFormatSerializer();

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type type, int features){
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeString("");
            return;
        }
        writeDate(object,out);
    }

    @Override
    public void doWrite(Long time, SerializeWriter out) {
        if (TimeUtil.format(time, NOW_YEAR_PATTERN).equals(TimeUtil.format(System.currentTimeMillis(), NOW_YEAR_PATTERN))){
            out.writeString(TimeUtil.format(time, "MM-dd"));
        }else {
            out.writeString(TimeUtil.format(time, "yyyy-MM-dd"));
        }
    }
}
