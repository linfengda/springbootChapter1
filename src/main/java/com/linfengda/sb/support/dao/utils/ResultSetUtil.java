package com.linfengda.sb.support.dao.utils;


import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            String fName = field.getName();
            String dbFieldName = ClassUtil.convert(fName);
            Object value = result.getObject(dbFieldName);
            setValue(field, o, value);
        }
    }

    private static void setValue(Field field,Object po,Object value) throws SecurityException, IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        field.set(po, value);
    }
}
