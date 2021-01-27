package com.linfengda.sb.support.serializable.jackson.money;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;


/**
 * @description 金额保留2位小数序列化类
 * @author linfengda
 * @date 2020-08-22 18:51
 */
public class BigDecimalMoneySerializer extends JsonSerializer<BigDecimal> {
    public static final BigDecimalMoneySerializer INSTANCE = new BigDecimalMoneySerializer();

    @Override
    public void serialize(BigDecimal money, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (null == money) {
            jsonGenerator.writeString("");
            return;
        }
        jsonGenerator.writeString(money.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString());
    }
}
