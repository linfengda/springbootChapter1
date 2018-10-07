package com.linfengda.sb.support.dao;

import com.linfengda.sb.support.dao.entity.AttributeValue;
import com.linfengda.sb.support.dao.entity.ConditionParam;
import com.linfengda.sb.support.dao.entity.PageInfo;
import com.linfengda.sb.support.dao.entity.SetValue;
import com.linfengda.sb.support.dao.service.AbstractBaseService;
import com.linfengda.sb.support.dao.sql.PreStatmentSqlBuilder;
import com.linfengda.sb.support.dao.sql.bo.PreStatementSql;
import com.linfengda.sb.support.dao.sql.handler.SqlStatement;
import com.linfengda.sb.support.dao.sql.handler.impl.PreStatementHandler;
import com.linfengda.sb.support.dao.utils.ClassUtil;
import com.linfengda.sb.support.dao.utils.ResultSetUtil;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;

/**
 * base dao service
 * 
 * @author liucn
 *
 */
public class BaseService extends AbstractBaseService {

	@Resource
	private DataSource dataSource;

	public BaseService(DataSource dataSource){
		this.dataSource = dataSource;
	}

	public BaseService(){}

	@SuppressWarnings("unchecked")
	public void save(Object po) throws Exception {
		SqlStatement statement = null;
		try {
		//batch save
		if(po instanceof List) {
			List<Object> poList = (List<Object>)po;
			this.batchSave(poList);
			return ;
		}
		String idName = ClassUtil.getIdName(po.getClass());
		AttributeValue idValue = ClassUtil.getValueByProperty(idName,po);
		PreStatementSql preSql;
		if (idValue.getValue() == null) {
			//simple save
			this.addDefaultValue(po);
			preSql = PreStatmentSqlBuilder.INSTANCE.buildInsert(po);
		} else {
			Long id = (Long)idValue.getValue();
			if(id.longValue() == 0L){
				//simple save
				this.addDefaultValue(po);
				preSql = PreStatmentSqlBuilder.INSTANCE.buildInsert(po);
			}else {
				//simple update
				this.updateDefaultValue(po);
				preSql = PreStatmentSqlBuilder.INSTANCE.buildUpdate(idValue,po);
			}
		}
			statement = new PreStatementHandler(preSql,dataSource);
			statement.executeUpdate();
		} finally {
			if(statement != null) {
				statement.close();
			}
		}
	}

	public void update(Class<?> clazz,SetValue setValue,ConditionParam conditionParam) throws Exception{
		SqlStatement statement = null;
		try {
			PreStatementSql preSql = PreStatmentSqlBuilder.INSTANCE.buildUpdate(setValue,conditionParam,clazz);
			statement = new PreStatementHandler(preSql,dataSource);
			statement.executeUpdate();
		}finally {
			if(statement != null) {
				statement.close();
			}
		}
	}

	public void updateByPrimaryKey(Class<?> clazz,SetValue setValue,Long id) throws Exception {
		String idName = ClassUtil.getIdName(clazz);
		if(idName == null || idName.equals("")) {
			throw new Exception(clazz+" 类没有ID属性");
		}
		ConditionParam conditionParam = new ConditionParam();
		conditionParam.add(idName,id);
		update(clazz,setValue,conditionParam);
	}

	public void delete(Object po) throws Exception {
		PreStatementSql preSql = PreStatmentSqlBuilder.INSTANCE.buildDeleteOne(po);
		SqlStatement statement = null;
		try {
			statement = new PreStatementHandler(preSql,dataSource);
			statement.executeUpdate();
		}finally {
			if(statement != null) {
				statement.close();
			}
		}
	}

	public <T> PageInfo<T> findPageInfoByParam(ConditionParam param, Class<T> clazz) throws Exception{
		SqlStatement statement = null;
		PageInfo<T> pageInfo = new PageInfo<T>();
		try {
			long totalRecorder = this.countByParam(param,clazz);
			int totalPage = this.getTotalPage(param.getPageSize(),totalRecorder);
			if (totalRecorder == 0L){
				pageInfo.setPageNo(param.getPageNo());
				pageInfo.setPageSize(param.getPageSize());
				pageInfo.setTotalRecoder(totalRecorder);
				pageInfo.setTotalPage(totalPage);
				pageInfo.setRecoders(Collections.EMPTY_LIST);
				return pageInfo;
			}

			//分页合理化
			if (param.getPageNo() > totalPage){
				param.setPageNo(totalPage);
			} else if (param.getPageNo() <= 0){
				param.setPageNo(1);
			}

			PreStatementSql preSql = PreStatmentSqlBuilder.INSTANCE.getQuerySqlByConditionParam(param,clazz,true);
			statement = new PreStatementHandler(preSql, dataSource);
			ResultSet result = statement.executeQuery();
			List recorders = ResultSetUtil.convertToListObject(clazz,result);

			pageInfo.setPageNo(param.getPageNo());
			pageInfo.setPageSize(param.getPageSize());
			pageInfo.setTotalRecoder(totalRecorder);
			pageInfo.setTotalPage(totalPage);
			pageInfo.setRecoders(recorders);
			return  pageInfo;
		}finally {
			if(statement != null) {
				statement.close();
			}
		}
	}

	public <T> List<T> findAll(ConditionParam param,Class<T> clazz) throws Exception{
		SqlStatement statement = null;
		try {
			PreStatementSql preSql = PreStatmentSqlBuilder.INSTANCE.getQuerySqlByConditionParam(param,clazz,false);
			statement = new PreStatementHandler(preSql, dataSource);
			ResultSet result = statement.executeQuery();
			List<T> recorders = ResultSetUtil.convertToListObject(clazz,result);

			return recorders;
		}finally {
			if(statement != null) {
				statement.close();
			}
		}
	}

	public <T> T findOne(ConditionParam param,Class<T> clazz) throws Exception{
		param.setPageNo(1);
		param.setPageSize(1);
		SqlStatement statement = null;
		try {
			PreStatementSql preSql = PreStatmentSqlBuilder.INSTANCE.getQuerySqlByConditionParam(param,clazz,true);
			statement = new PreStatementHandler(preSql, dataSource);
			ResultSet result = statement.executeQuery();
			return ResultSetUtil.convertToObject(clazz,result);
		} finally {
			if(statement != null) {
				statement.close();
			}
		}
	}

	public long countByParam(ConditionParam param,Class<?> clazz) throws Exception{
		SqlStatement statement = null;
		try {
			PreStatementSql preSql = PreStatmentSqlBuilder.INSTANCE.getCountSqlByConditionParam(param,clazz);
			statement = new PreStatementHandler(preSql, dataSource);
			ResultSet result = statement.executeQuery();
			return ResultSetUtil.getLong("c",result);
		}finally {
			if(statement != null) {
				statement.close();
			}
		}
	}

	public boolean isExist(ConditionParam param,Class<?> clazz)throws Exception{
		Long count = this.countByParam(param,clazz);
		return count.longValue() >= 1L;
	}

    public String queryMaxFlow(String sql, List<AttributeValue> params) throws Exception {
        SqlStatement statement = null;
        try {
            PreStatementSql preSql = new PreStatementSql();
            preSql.setSql(sql);
            preSql.setParams(params);
			statement = new PreStatementHandler(preSql, dataSource);
            ResultSet result = statement.executeQuery();
            return ResultSetUtil.getString("maxFlow",result);
        } finally {
            if (statement != null) {
				statement.close();
            }
        }
    }

	public <T> T findByPrimaryKey(Long id, Class<T> clazz) throws Exception {
		SqlStatement statement = null;
		try {
			PreStatementSql preSql = PreStatmentSqlBuilder.INSTANCE.getQuerySqlById(id, clazz);
			statement = new PreStatementHandler(preSql,dataSource);
			ResultSet result = statement.executeQuery();
			return ResultSetUtil.convertToObject(clazz,result);
		} finally {
			if(statement != null) {
				statement.close();
			}
		}
	}

	/**
	 * 批量新增
	 * @param poList
	 * @throws Exception
	 */
	public void batchSave(List<Object> poList) throws Exception {
		if (poList == null || poList.size() == 0){
			return;
		}
		for (Object po : poList) {
			this.addDefaultValue(po);
		}

		this.doBatchSave(poList);
	}

	private void doBatchSave(List<Object> saveList) throws Exception{
		SqlStatement statement = null;
		try {
			PreStatementSql preSql = PreStatmentSqlBuilder.INSTANCE.buildInsert(saveList);
			statement = new PreStatementHandler(preSql,dataSource);
			statement.executeBatchSave();
		}finally {
			if(statement != null) {
				statement.close();
			}
		}
	}

	private int getTotalPage(int pageSize,long totalRecorder){
		if(totalRecorder == 0){
			return  0;
		}else {
			if (totalRecorder%pageSize == 0){
				return  (int)totalRecorder/pageSize;
			}else {
				return  (int)totalRecorder/pageSize+1;
			}
		}
	}
}
