package com.linfengda.sb.support.serializer.serializer.fastjson.money;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;


/**
 * @description: 金额保留2位小数序列化类
 * @author: linfengda
 * @date: 2020-08-22 18:51
 */
public class BigDecimalMoneySerializer implements ObjectSerializer {
    public static final BigDecimalMoneySerializer INSTANCE = new BigDecimalMoneySerializer();
    private static final DecimalFormat DF = new DecimalFormat("###.00");

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeString("");
            return;
        }
        if (!(object instanceof BigDecimal)){
            return;
        }
        BigDecimal bigDecimal = (BigDecimal)object;
        if (bigDecimal.compareTo(BigDecimal.ZERO) == 0){
            out.writeString("0.00");
        }else {
            out.writeString(bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString());
        }
    }
}
