package com.linfengda.sb.support.serializable.jackson.date;

/**
 * @description: 日期输出序列化类
 * @author: linfengda
 * @date: 2020-12-16 16:07
 */
public class TimeFormatYMdSerializer extends TimeFormatSerializer {

    @Override
    protected String pattern() {
        return "yyyy-MM-dd";
    }
}
