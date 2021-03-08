package com.linfengda.sb.support.serializable.jackson.date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.linfengda.sb.chapter1.common.util.TimeUtil;

import java.io.IOException;

/**
 * @description 日期输出序列化类
 * @author linfengda
 * @date 2020-08-22 18:51
 */
public class TimeFormatMdSerializer extends TimeFormatSerializer {
    private static final String NOW_YEAR_PATTERN = "yyyy";


    @Override
    public void serialize(Object time, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Long ms = convert(time);
        if (null == ms || 0 == ms) {
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
