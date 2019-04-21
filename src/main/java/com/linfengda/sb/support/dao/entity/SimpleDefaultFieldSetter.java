package com.linfengda.sb.support.dao.entity;

import com.linfengda.sb.support.dao.utils.ClassUtil;
import com.linfengda.sb.support.dao.utils.UserUtil;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.List;

/**
 * 描述: 默认字段填充
 *
 * @author linfengda
 * @create 2019-04-12 13:25
 */
public class SimpleDefaultFieldSetter implements DefaultFieldSetter {

    @Override
    public void addDefaultValue(Object po) throws Exception {
        List<Field> fields =  ClassUtil.getFields(po.getClass());
        for (Field field : fields) {
            if(field.getName().equals(CREATE_TIME)) {
                field.setAccessible(true);
                field.set(po, new Timestamp(System.currentTimeMillis()));
            }
            if (field.getName().equals(CREATE_USER)){
                field.setAccessible(true);
                field.set(po, UserUtil.getCurrentUserId());
            }
            if (field.getName().equals(UPDATE_TIME)){
                field.setAccessible(true);
                field.set(po, new Timestamp(System.currentTimeMillis()));
            }
            if (field.getName().equals(UPDATE_USER)){
                field.setAccessible(true);
                field.set(po, UserUtil.getCurrentUserId());
            }
            if (field.getName().equals(IS_DELETE)){
                field.setAccessible(true);
                field.set(po, BasePO.Delete.NORMAL.getCode());
            }
            if (field.getName().equals(VERSION)){
                field.setAccessible(true);
                field.set(po, 1);
            }
        }
    }

    @Override
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
