package com.linfengda.sb.support.dao.entity;

/**
 * 描述: 默认字段填充
 *
 * @author linfengda
 * @create 2019-04-12 13:25
 */
public interface DefaultFieldSetter extends DefaultField {
	/**
	 * 设置默认字段值
	 * @param po
	 * @throws Exception
	 */
    void addDefaultValue(Object po) throws Exception;

    /**
     * 更新默认字段值
     * @param po
     * @throws IllegalAccessException
     */
    void updateDefaultValue(Object po) throws IllegalAccessException;
}
