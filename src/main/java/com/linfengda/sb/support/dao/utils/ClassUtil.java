package com.linfengda.sb.support.dao.utils;

import com.linfengda.sb.support.dao.annontation.Id;
import com.linfengda.sb.support.dao.annontation.Table;
import com.linfengda.sb.support.dao.entity.AttributeValue;
import com.linfengda.sb.support.dao.entity.type.BaseType;
import com.linfengda.sb.support.dao.entity.type.SimpleBaseType;
import com.linfengda.sb.support.dao.exception.DataAccessException;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: liugenhua
 * @Date created in 2018/5/24 19:03
 */
public class ClassUtil {

	/**
	 * 获取所有成员变量
	 * @param clazz
	 * @return
	 */
	public static List<Field> getFields(Class<?> clazz){
		List<Field> fieldList = new ArrayList<Field>();
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
		Annotation[] anns = clazz.getAnnotations();
		for (Annotation annotation : anns) {
			if(annotation.annotationType().equals(Table.class)) {
				Table tableAnno = (Table)annotation;
				return tableAnno.name();
			}
		}
		return "none";
	}
	
	public static String getIdName(Class<?> clazz) {
		List<Field> fields = getFields(clazz);
		return getIdName(fields);
	}
	
	public static String getIdName(List<Field> fields) {
		for (Field field : fields) {
			Annotation[] annos =  field.getAnnotations();
			if(annos != null && annos.length > 0) {
				for (Annotation annotation : annos) {
					if(annotation.annotationType().equals(Id.class)) {
						return field.getName();
					}
				}
			}
		}

		throw new DataAccessException("没有发现id属性.");
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
		String type = clazz.getName();
		BaseType baseType = BaseType.getBaseType(type);
		switch (baseType) {
		case STRING:return SimpleBaseType.STRING.getValue();
		case INT:return SimpleBaseType.INT.getValue();
		case B_INT:return SimpleBaseType.INT.getValue();
		case LONG:return SimpleBaseType.LONG.getValue();
		case B_LONG:return SimpleBaseType.LONG.getValue();
		case DOUBLE:return SimpleBaseType.DOUBLE.getValue();
		case B_DOUBLE:return SimpleBaseType.DOUBLE.getValue();
		case BIG_DECIMAL:return SimpleBaseType.BIG_DECIMAL.getValue();
		case DATE:return SimpleBaseType.DATE.getValue();
		case SQL_DATE:return SimpleBaseType.SQL_DATE.getValue();
		case SQL_DATE_TIME:return SimpleBaseType.SQL_DATE_TIME.getValue();
		case BYTE:return SimpleBaseType.BYTE.getValue();
		case B_BYTE:return SimpleBaseType.BYTE.getValue();
		case BYTES:return SimpleBaseType.BYTES.getValue();
		default:
			break;
		}
		return "";
	}
	
}
