package com.linfengda.sb.support.orm.utils;

import com.linfengda.sb.support.orm.annontation.Id;
import com.linfengda.sb.support.orm.annontation.Table;
import com.linfengda.sb.support.orm.entity.AttributeValue;
import com.linfengda.sb.support.orm.exception.DataAccessException;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 获取类属性
 *
 * @author linfengda
 * @create 2019-04-12 13:25
 */
public class ClassUtil {

	/**
	 * 获取所有成员变量
	 * @param clazz
	 * @return
	 */
	public static List<Field> getFields(Class<?> clazz){
		List<Field> fieldList = new ArrayList<>();
        while(true) {
        	Field[] fields = clazz.getDeclaredFields();;
        	for (Field field : fields) {
        		fieldList.add(field);
			}
        	Class<?> supperClazz = clazz.getSuperclass();
		 	if(supperClazz != null) {
		 		clazz = supperClazz;
		 	} else {//没有父类 结束迭代
		 		break ;
		 	}
		}
        
        return fieldList;
	}
	
	public static String getTableName(Class<?> clazz) {
		Annotation[] annotations = clazz.getAnnotations();
		for (Annotation annotation : annotations) {
			if(annotation.annotationType().equals(Table.class)) {
				Table tableAnno = (Table)annotation;
				String tableName = tableAnno.name();
				if (0 != tableName.length()) {
					return tableName;
				}
			}
		}
		throw new DataAccessException("没有发现table属性！");
	}
	
	public static String getIdName(Class<?> clazz) {
		List<Field> fields = getFields(clazz);
        for (Field field : fields) {
            Annotation[] annotations =  field.getAnnotations();
            if(annotations != null && annotations.length > 0) {
                for (Annotation annotation : annotations) {
                    if(annotation.annotationType().equals(Id.class)) {
                        return field.getName();
                    }
                }
            }
        }
        throw new DataAccessException("没有发现id属性！");
	}
	
	/**
	 * 按属性名获取属性值
	 * @param FieldName
	 * @param object
	 * @param clazz
	 * @return
	 */
	public static AttributeValue getValueByProperty(String FieldName, Object object, Class<?> clazz) {
		try {
			PropertyDescriptor pd = new PropertyDescriptor(FieldName, clazz);
			Method getMethod = pd.getReadMethod();
			Object o = getMethod.invoke(object);
			
			return new AttributeValue(o,convertType(pd.getPropertyType()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 按属性名获取属性值
	 * @param FieldName
	 * @param object
	 * @return
	 */
	public static AttributeValue getValueByProperty(String FieldName,Object object) {
		return  getValueByProperty(FieldName,object,object.getClass());
	}
	
	public static String convertType(Class<?> clazz) {
		return "";
	}

	/**
	 * 转化成驼峰命名
	 * @param fieldName
	 * @return
	 */
	public static String convert(String fieldName) {
		char [] cs = fieldName.toCharArray();
		String targetName = "";
		for (char c : cs) {
			if(Character.isUpperCase(c)) {
				targetName += "_"+Character.toLowerCase(c);
			} else {
				targetName += c;
			}
		}
		return targetName;
	}

	public static void setValue(Field field,Object po,Object value) throws SecurityException, IllegalArgumentException, IllegalAccessException {
		field.setAccessible(true);
		field.set(po, value);
	}
}
