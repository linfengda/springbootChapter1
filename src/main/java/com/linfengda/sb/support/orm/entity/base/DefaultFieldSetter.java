package com.linfengda.sb.support.orm.entity.base;

import com.linfengda.sb.support.orm.utils.ClassUtil;
import com.linfengda.sb.support.orm.utils.UserUtil;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.List;

/**
 * 描述: 默认字段填充
 *
 * @author linfengda
 * @create 2019-04-12 13:25
 */
public class DefaultFieldSetter implements DefaultField {

	/**
	 * 创建PO默认字段值
	 * @param po
	 * @throws IllegalAccessException
	 */
	public void addDefaultValue(Object po) throws IllegalAccessException {
		List<Field> fields =  ClassUtil.getFields(po.getClass());
		for (Field field : fields) {
			if(field.getName().equals(CREATE_TIME) || field.getName().equals(UPDATE_TIME)) {
				field.setAccessible(true);
				field.set(po, new Timestamp(System.currentTimeMillis()));
			}
			if (field.getName().equals(CREATE_USER) || field.getName().equals(UPDATE_USER)){
				field.setAccessible(true);
				field.set(po, UserUtil.getCurrentUserId());
			}
			if (field.getName().equals(DELETE)){
				field.setAccessible(true);
				field.set(po, BasePO.Delete.NORMAL.getCode());
			}
			if (field.getName().equals(VERSION)){
				field.setAccessible(true);
				field.set(po, 1);
			}
		}
	}

    /**
     * 更新PO默认字段值
     * @param po
     * @throws IllegalAccessException
     */
	public void updateDefaultValue(Object po) throws IllegalAccessException {
		List<Field> fields =  ClassUtil.getFields(po.getClass());
		for (Field field : fields) {
			if (field.getName().equals(UPDATE_TIME)){
				field.setAccessible(true);
				field.set(po, new Timestamp(System.currentTimeMillis()));
			}
			if (field.getName().equals(UPDATE_USER)){
				field.setAccessible(true);
				field.set(po, UserUtil.getCurrentUserId());
			}
			if (field.getName().equals(VERSION)){
				Object value = ClassUtil.getValueByProperty(field.getName(),po).getValue();
				Integer version = (Integer)value;
				if (version == null){
					version = 1;
				}else {
					version = version+1;
				}
				field.setAccessible(true);
				field.set(po, version);
			}
		}
	}
}
