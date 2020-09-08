package com.linfengda.sb.support.serializer.serializer.jackson.date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.linfengda.sb.support.serializer.serializer.fastjson.date.DateFormatMdSerializer;
import com.linfengda.sb.support.util.TimeUtil;

import java.io.IOException;

/**
 * @description: 日期输出序列化类
 * @author: linfengda
 * @date: 2020-08-23 23:15
 */
public class SecondFormatSerializer extends JsonSerializer<Integer> {
    public static final DateFormatMdSerializer INSTANCE = new DateFormatMdSerializer();

    @Override
    public void serialize(Integer time, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Long ms = TimeUtil.second2Ms(time);
        if (null == ms || 0 == ms.intValue()) {
            jsonGenerator.writeString("");
            return;
        }
        jsonGenerator.writeString(TimeUtil.format(ms, "yyyy-MM-dd HH:mm:ss"));
    }
}
