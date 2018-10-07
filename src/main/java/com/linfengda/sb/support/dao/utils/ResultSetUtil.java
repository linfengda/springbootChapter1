package com.linfengda.sb.support.dao.utils;

import com.linfengda.sb.support.dao.entity.type.SimpleBaseType;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:数据查询集处理类
 * @Author: liugenhua
 * @Date created in 2018/6/12 10:08
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

    public static String getString(String columnLabel,ResultSet result)throws Exception {
        while (result.next()) {
            return result.getString(columnLabel);
        }
        return  null;
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
            String fName = field.getName();
            String dbFieldName = NameUtil.convert(fName);
            SimpleBaseType simpleBaseType = SimpleBaseType.getBaseType(ClassUtil.convertType(field.getType()));
            switch (simpleBaseType) {
                case STRING:
                    String value = result.getString(dbFieldName);
                    setValue(field, o, value);
                    break;
                case INT:
                    Integer intValue = result.getInt(dbFieldName);
                    setValue(field, o, intValue);
                    break;
                case LONG:
                    Long longValue = result.getLong(dbFieldName);
                    setValue(field, o, longValue);
                    break;
                case DOUBLE:
                    Double doubleValue = result.getDouble(dbFieldName);
                    setValue(field, o, doubleValue);
                    break;
                case BIG_DECIMAL:
                    BigDecimal bigDecimalValue = result.getBigDecimal(dbFieldName);
                    setValue(field, o, bigDecimalValue);
                    break;
                case FLOAT:
                    Float floatValue = result.getFloat(dbFieldName);
                    setValue(field, o, floatValue);
                    break;
                case DATE:
                    Timestamp timestamp = result.getTimestamp(dbFieldName);
                    if (timestamp != null) {
                        setValue(field, o, new java.util.Date(timestamp.getTime()));
                    }
                    break;
                case SQL_DATE:
                    Timestamp dt = result.getTimestamp(dbFieldName);
                    setValue(field, o, dt);
                    break;
                case SQL_DATE_TIME:
                    Timestamp sdt = result.getTimestamp(dbFieldName);
                    setValue(field, o, sdt);
                    break;
                case BYTES:
                    byte [] bytes = result.getBytes(dbFieldName);
                    setValue(field, o, bytes);
                    break;
                case BYTE:
                    byte b = result.getByte(dbFieldName);
                    setValue(field, o, b);
                    break;
                default:
                    break;
            }
        }
    }

    private static void setValue(Field field,Object po,Object value) throws SecurityException, IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        field.set(po, value);
    }
}
