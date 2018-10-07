package com.linfengda.sb.support.dao.service;

import com.linfengda.sb.support.dao.entity.BasePO;
import com.linfengda.sb.support.dao.entity.CommonField;
import com.linfengda.sb.support.dao.utils.ClassUtil;
import com.linfengda.sb.support.dao.utils.UserUtil;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * @Description:
 * @Author: liugenhua
 * @Date created in 2018/4/20 19:03
 */
public abstract class AbstractBaseService{
	/**
	 * 设置默认字段值
	 * @param po
	 * @throws Exception
	 */
	protected void addDefaultValue(Object po) throws Exception {
		List<Field> fields =  ClassUtil.getFields(po.getClass());
		String idName = ClassUtil.getIdName(fields);
		for (Field field : fields) {
			//初始创建时间
			if(field.getName().equals(CommonField.CREATE_TIME)) {
				field.setAccessible(true);
				field.set(po, new Timestamp(System.currentTimeMillis()));
			}
			if (field.getName().equals(CommonField.CREATE_USER)){
				field.setAccessible(true);
				field.set(po, UserUtil.getCurrentUserId());
			}
			if (field.getName().equals(CommonField.UPDATE_TIME)){
				field.setAccessible(true);
				field.set(po, new Timestamp(System.currentTimeMillis()));
			}
			if (field.getName().equals(CommonField.UPDATE_USER)){
				field.setAccessible(true);
				field.set(po, UserUtil.getCurrentUserId());
			}
			if (field.getName().equals(CommonField.IS_DELETE)){
				field.setAccessible(true);
				field.set(po, BasePO.Delete.NORMAL.getCode());
			}
			if (field.getName().equals(CommonField.VERSION)){
				field.setAccessible(true);
				field.set(po, 1);
			}
			//初始id值
			if(field.getName().equals(idName)) {
				field.setAccessible(true);
				field.set(po, UUID.randomUUID().toString());
			}
		}
	}

	protected void updateDefaultValue(Object po) throws IllegalAccessException {
		List<Field> fields =  ClassUtil.getFields(po.getClass());
		for (Field field : fields) {
			if (field.getName().equals(CommonField.UPDATE_TIME)){
				field.setAccessible(true);
				field.set(po, new Timestamp(System.currentTimeMillis()));
			}
			if (field.getName().equals(CommonField.UPDATE_USER)){
				field.setAccessible(true);
				field.set(po, UserUtil.getCurrentUserId());
			}
			if (field.getName().equals(CommonField.VERSION)){
				Object value = ClassUtil.getValueByProperty(field.getName(),po).getValue();
				Integer value_l = (Integer)value;
				if (value_l == null){
					value_l = 1;
				}else {
					value_l = value_l+1;
				}

				field.setAccessible(true);
				field.set(po, value_l);
			}
		}
	}

}
