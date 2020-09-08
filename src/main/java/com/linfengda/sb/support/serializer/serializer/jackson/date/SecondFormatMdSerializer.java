package com.linfengda.sb.support.serializer.serializer.jackson.date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.linfengda.sb.support.util.TimeUtil;

import java.io.IOException;

/**
 * @description: 日期输出序列化类
 * @author: linfengda
 * @date: 2020-08-22 18:51
 */
public class SecondFormatMdSerializer extends JsonSerializer<Integer> {
    private static final String NOW_YEAR_PATTERN = "yyyy";
    public static final SecondFormatMdSerializer INSTANCE = new SecondFormatMdSerializer();


    @Override
    public void serialize(Integer time, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Long ms = TimeUtil.second2Ms(time);
        if (null == ms || 0 == ms.intValue()) {
            jsonGenerator.writeString("");
            return;
        }
        if (TimeUtil.format(ms, NOW_YEAR_PATTERN).equals(TimeUtil.format(System.currentTimeMillis(), NOW_YEAR_PATTERN))){
            jsonGenerator.writeString(TimeUtil.format(ms, "MM-dd"));
        }else {
            jsonGenerator.writeString(TimeUtil.format(ms, "yyyy-MM-dd"));
        }
    }
}
