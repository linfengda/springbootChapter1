package com.linfengda.sb.support.orm.utils;


import com.linfengda.sb.support.orm.type.BaseType;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 数据查询集处理类
 *
 * @author linfengda
 * @create 2019-04-12 13:25
 */
public class ResultSetUtil {

    public static <T> List<T> convertToListObject(Class<T> clazz,ResultSet result) throws Exception {
        List recorders = new ArrayList();
        while (result.next()) {
            T object = clazz.newInstance();
            setRecorderValue(object, result);
            recorders.add(object);
        }
        return recorders;
    }

    public static <T> T convertToObject(Class<T> clazz,ResultSet result) throws Exception {
        while (result.next()) {
            T object = clazz.newInstance();
            setRecorderValue(object, result);
            return object;
        }
        return null;
    }

    public static Long getLong(String columnLabel,ResultSet result)throws Exception {
        while (result.next()) {
            return result.getLong(columnLabel);
        }
        return  0L;
    }

    /**
     * 数据库结果集映射到实体
     *
     * @param o
     * @param result
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws SQLException
     */
    private static void setRecorderValue(Object o, ResultSet result) throws SecurityException,
            IllegalArgumentException, IllegalAccessException, SQLException {

        List<Field> fields = ClassUtil.getFields(o.getClass());
        for (Field field : fields) {
            String dbFieldName = ClassUtil.convert(field.getName());
            BaseType baseType = BaseType.getBaseType(field.getType().getName());
            switch (baseType) {
                case STRING:
                    ClassUtil.setValue(field, o, result.getString(dbFieldName));
                    break;
                case INT:
                    ClassUtil.setValue(field, o, result.getInt(dbFieldName));
                    break;
                case B_INT:
                    Object bi = result.getObject(dbFieldName);
                    if (null != bi){
                        ClassUtil.setValue(field, o, result.getInt(dbFieldName));
                    }
                    break;
                case LONG:
                    ClassUtil.setValue(field, o, result.getLong(dbFieldName));
                    break;
                case B_LONG:
                    Object bl = result.getObject(dbFieldName);
                    if (null != bl){
                        ClassUtil.setValue(field, o, Long.valueOf(bl.toString()));
                    }
                    break;
                case DOUBLE:
                    ClassUtil.setValue(field, o, result.getDouble(dbFieldName));
                    break;
                case B_DOUBLE:
                    Object bd = result.getObject(dbFieldName);
                    if (null != bd){
                        if (bd instanceof BigDecimal){
                            BigDecimal d = (BigDecimal)bd;
                            ClassUtil.setValue(field, o, d.doubleValue());
                        }else {
                            ClassUtil.setValue(field, o, bd);
                        }
                    }
                    break;
                case BIG_DECIMAL:
                    Object bdc = result.getObject(dbFieldName);
                    if (null != bdc){
                        if (bdc instanceof Double){
                            Double dou = (Double)bdc;
                            ClassUtil.setValue(field,o,new BigDecimal(dou.toString()));
                        }else {
                            ClassUtil.setValue(field, o, bdc);
                        }
                    }
                    break;
                case FLOAT:
                    ClassUtil.setValue(field, o, result.getFloat(dbFieldName));
                    break;
                case B_FLOAT:
                    Object bf = result.getObject(dbFieldName);
                    if (null != bf){
                        ClassUtil.setValue(field, o, Float.valueOf(bf.toString()));
                    }
                    break;
                case DATE:
                    Timestamp timestamp = result.getTimestamp(dbFieldName);
                    if (timestamp != null) {
                        ClassUtil.setValue(field, o, new java.util.Date(timestamp.getTime()));
                    }
                    break;
                case SQL_DATE:
                    ClassUtil.setValue(field, o, result.getDate(dbFieldName));
                    break;
                case SQL_TIMESTAMP:
                    ClassUtil.setValue(field, o, result.getTimestamp(dbFieldName));
                    break;
                case BYTE:
                    ClassUtil.setValue(field, o, result.getByte(dbFieldName));
                    break;
                case B_BYTE:
                    ClassUtil.setValue(field, o, result.getObject(dbFieldName));
                    break;
                case BOOLEAN:
                    ClassUtil.setValue(field, o, result.getBoolean(dbFieldName));
                    break;
                case B_BOOLEAN:
                    ClassUtil.setValue(field, o, result.getBoolean(dbFieldName));
                    break;
                default:
                    break;
            }
        }
    }
}
