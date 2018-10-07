package com.linfengda.sb.support.dao.sql;

import com.linfengda.sb.support.dao.entity.AttributeValue;
import com.linfengda.sb.support.dao.entity.CommonField;
import com.linfengda.sb.support.dao.entity.ConditionParam;
import com.linfengda.sb.support.dao.entity.SetValue;
import com.linfengda.sb.support.dao.entity.type.SimpleBaseType;
import com.linfengda.sb.support.dao.exception.DataAccessException;
import com.linfengda.sb.support.dao.sql.bo.PreStatementSql;
import com.linfengda.sb.support.dao.utils.ClassUtil;
import com.linfengda.sb.support.dao.utils.NameUtil;
import com.linfengda.sb.support.dao.utils.UserUtil;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public enum PreStatmentSqlBuilder{
	INSTANCE;

	/**
	 * 绑定插入sql语句
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public PreStatementSql buildInsert(Object po) throws Exception {
		PreStatementSql preSql = new PreStatementSql();
		List<Field> fields = ClassUtil.getFields(po.getClass());
		preSql.setSql(getInsertSqlStr(fields, po.getClass()));
		
		// 参数
		List<AttributeValue> params = new ArrayList<>();
		for (Field field : fields) {
			AttributeValue aValue = ClassUtil.getValueByProperty(field.getName(), po, po.getClass());
			params.add(aValue);
		}
		
		preSql.setParams(params);
		return preSql;
	}

	public PreStatementSql buildUpdate(AttributeValue idValue, Object po) throws Exception{
		PreStatementSql preSql = new PreStatementSql();
		Class clazz = po.getClass();
		List<Field> fields = ClassUtil.getFields(clazz);
		preSql.setSql(getUpdateSqlStr(fields,clazz));
		preSql.setParams(this.getUpdateParam(fields,po,idValue,clazz));
		return  preSql;
	}

	private List<AttributeValue> getUpdateParam(List<Field> fields,Object po,AttributeValue idValue,Class<?> clazz){
		// 参数
		List<AttributeValue> params = new ArrayList<>();
		for (Field field : fields) {
			AttributeValue aValue = ClassUtil.getValueByProperty(field.getName(), po, clazz);
			params.add(aValue);
		}
		//add id value
		params.add(idValue);
		//add version
		AttributeValue versionValue = ClassUtil.getValueByProperty("version",po,clazz);
		if (versionValue == null){
			throw new DataAccessException("po实体version为空");
		}
		Integer version = (Integer) versionValue.getValue();
		if (version == null) {
			throw new DataAccessException("po实体version为空");
		}
		version = version-1;
		versionValue.setValue(version);

		params.add(versionValue);
		return params;
	}

	/**
	 * 绑定批量插入sql语句
	 * @param pos
	 * @return
	 * @throws Exception
	 */
	public PreStatementSql buildInsert(List<Object> pos) throws Exception {
		Object po = null;
		if(pos != null && pos.size() > 0) {
			po = pos.get(0);
		}
		Class clazz = po.getClass();

		PreStatementSql preSql = new PreStatementSql();
		List<Field> fields = ClassUtil.getFields(clazz);
		preSql.setSql(getInsertSqlStr(fields, clazz));
		preSql.setBatchParams(getInsertParamForBatch(pos));
		
		return preSql;
	}

	/**
	 * 按条件更新记录
	 * @param setValue
	 * @param conditionParam
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public PreStatementSql buildUpdate(SetValue setValue, ConditionParam conditionParam, Class<?> clazz) throws Exception {
		StringBuilder sql = new StringBuilder("UPDATE "+getTableName(clazz));
		List<Field> fields =  ClassUtil.getFields(clazz);
		for (Field field:fields) {
			if (field.getName().equals(CommonField.UPDATE_TIME)){
				setValue.add(CommonField.UPDATE_TIME,new Timestamp(System.currentTimeMillis()));
			}
			if (field.getName().equals(CommonField.UPDATE_USER)){
				setValue.add(CommonField.UPDATE_USER, UserUtil.getCurrentUserId());
			}
		}
		sql.append(setValue.getPartSql());
		sql.append(conditionParam.getConditionSql());

		List<AttributeValue> params = new ArrayList<>();
		for (AttributeValue attr:setValue.getParams()) {
			params.add(attr);
		}
		for (AttributeValue attr:conditionParam.getParams()) {
			params.add(attr);
		}

		PreStatementSql preSql = new PreStatementSql();
		preSql.setSql(sql.toString());
		preSql.setParams(params);
		return preSql;
	}

	/**
	 * 按id删除一条记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public PreStatementSql buildDeleteOne(Object po) throws Exception{
		Class clazz = po.getClass();
		PreStatementSql preSql = new PreStatementSql();
		String tableName = getTableName(clazz);
		String idName = ClassUtil.getIdName(clazz);
		preSql.setSql("DELETE FROM "+tableName+" WHERE "+NameUtil.convert(idName)+"=?");

		//参数
		List<AttributeValue> params = new ArrayList<>();
		AttributeValue aValue = ClassUtil.getValueByProperty(idName,po,clazz);
		params.add(aValue);
		preSql.setParams(params);

		return preSql;
	}

	/**
	 *按id查询单表数据
	 * @param Id
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public PreStatementSql getQuerySqlById(Long Id, Class<?> clazz) throws Exception {
		PreStatementSql preSql = new PreStatementSql();
		String tableName = getTableName(clazz);
		StringBuilder sql = new StringBuilder("SELECT * FROM ");
		sql.append(tableName + " ");
		sql.append("WHERE " + getIdName(clazz) + " = ?;");

		// 参数
		List<AttributeValue> params = new ArrayList<>();
		params.add(new AttributeValue(Id, SimpleBaseType.LONG.getValue()));

		preSql.setSql(sql.toString());
		preSql.setParams(params);
		return preSql;
	}

	public PreStatementSql getQuerySqlByConditionParam(ConditionParam param, Class<?> clazz, boolean isUsePage) throws Exception{
		PreStatementSql preSql = new PreStatementSql();
		String tableName = getTableName(clazz);

		StringBuilder sql = new StringBuilder("SELECT * FROM ");
		sql.append(tableName);

		if (isUsePage){
			preSql.setSql(sql+param.getPageConditionSql());
		}else {
			preSql.setSql(sql+param.getNotPageConditionSql());
		}

		preSql.setParams(param.getParams());
		return preSql;
	}

	public PreStatementSql getCountSqlByConditionParam(ConditionParam param, Class<?> clazz) throws Exception{
		PreStatementSql preSql = new PreStatementSql();
		String tableName = getTableName(clazz);

		StringBuilder sql = new StringBuilder("SELECT COUNT(1) as c FROM ");
		sql.append(tableName);

		preSql.setSql(sql + param.getConditionSql());
		preSql.setParams(param.getParams());
		return  preSql;
	}
	
	private List<List<AttributeValue>> getInsertParamForBatch(List<Object> pos){
		List<Field> fields = null;
		List<List<AttributeValue>> objectParams = new ArrayList<>();
		if(pos != null && pos.size() > 0) {
			fields = ClassUtil.getFields(pos.get(0).getClass());
		}
		for (Object object : pos) {
			// 参数
			List<AttributeValue> params = new ArrayList<>();
			for (Field field : fields) {
				AttributeValue aValue = ClassUtil.getValueByProperty(field.getName(), object, object.getClass());
				params.add(aValue);
			}
			objectParams.add(params);
		}
		
		return objectParams;
	}
	
	private String getInsertSqlStr(List<Field> fields, Class<?> clazz) throws Exception {
		int len = fields.size();
		// 追加字段名
		StringBuilder sql = new StringBuilder("INSERT INTO ");

		sql.append(getTableName(clazz) + "(");
		for (int i = 0; i < len; i++) {
			if (i == len - 1) {
				sql.append(NameUtil.convert(fields.get(i).getName()));
			} else {
				sql.append(NameUtil.convert(fields.get(i).getName()) + ",");
			}
		}

		// 追加参数占位符
		sql.append(") VALUES(");
		for (int i = 0; i < len; i++) {
			if (i == len - 1) {
				sql.append("?");
			} else {
				sql.append("?,");
			}
		}
		sql.append(");");
		
		return sql.toString();
	}

	//update t set a=?,b=? where id = ?
	private String getUpdateSqlStr(List<Field> fields, Class<?> clazz)throws Exception{
		int len = fields.size();
		String tableName = getTableName(clazz);
		StringBuilder sql = new StringBuilder("UPDATE "+ tableName);
		sql.append(" SET ");
		for (int i = 0; i < len; i++) {
			if (i == len - 1) {
				sql.append(NameUtil.convert(fields.get(i).getName())).append("=?");
			} else {
				sql.append(NameUtil.convert(fields.get(i).getName())).append("=?").append(",");
			}
		}
		sql.append(" WHERE ").append(NameUtil.convert(ClassUtil.getIdName(clazz))).append("=?");
		sql.append(" AND version=?");
		return  sql.toString();
	}

	private String getTableName(Class<?> clazz) throws Exception {
		String tableName = ClassUtil.getTableName(clazz);
		if(tableName == null || tableName.equals("none")) {
			throw new Exception("对象类型没有添加表名注解!");
		}

		return tableName;
	}

	private String getIdName(Class<?> clazz) throws Exception {
		String idName = ClassUtil.getIdName(clazz);
		if(idName == null || idName.equals("")) {
			throw new Exception(clazz+" 类没有ID属性");
		}
		idName = NameUtil.convert(idName);

		return idName;
	}
}
