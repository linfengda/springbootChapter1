package com.linfengda.sb.support.serializable.jackson.date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.linfengda.sb.support.exception.BusinessException;
import com.linfengda.sb.support.util.TimeUtil;

import java.io.IOException;
import java.time.ZoneOffset;

/**
 * @description: 日期输出序列化类
 * @author: linfengda
 * @date: 2020-08-23 23:15
 */
public class TimeFormatSerializer extends JsonSerializer<Object> {

    @Override
    public void serialize(Object time, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Long ms = convert(time);
        if (null == ms || 0 == ms) {
            jsonGenerator.writeString("");
            return;
        }
        jsonGenerator.writeString(TimeUtil.format(ms, pattern()));
    }

    protected Long convert(Object time) {
        if (null == time) {
            return null;
        }
        Long ms;
        String clazz = time.getClass().getName();
        if (clazz.equals("java.lang.Long")) {
            ms = (Long) time;
        }else if (clazz.equals("java.lang.Integer")) {
            ms = TimeUtil.second2Ms((Integer) time);
        }else if (clazz.equals("java.time.LocalDateTime")) {
            ms = ((java.time.LocalDateTime) time).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        }else if (clazz.equals("java.util.Date")) {
            ms = ((java.util.Date) time).getTime();
        }else if (clazz.equals("java.sql.Date")) {
            ms = ((java.sql.Date) time).getTime();
        }else if (clazz.equals("java.sql.Timestamp")) {
            ms = ((java.sql.Timestamp) time).getTime();
        }else {
            throw new BusinessException("不支持的时间类型！");
        }
        return ms;
    }

    protected String pattern() {
        return "yyyy-MM-dd HH:mm:ss";
    }
}
