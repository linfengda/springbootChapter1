package com.linfengda.sb.support.serializable.jackson.money;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @description 数量保留2位小数序列化类
 * @author linfengda
 * @date 2020-12-14 11:06
 */
public class BigDecimalNumberSerializer extends JsonSerializer<BigDecimal> {
    public static final BigDecimalMoneySerializer INSTANCE = new BigDecimalMoneySerializer();

    @Override
    public void serialize(BigDecimal number, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (null == number) {
            jsonGenerator.writeString("");
            return;
        }
        jsonGenerator.writeString(number.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString());
    }
}
