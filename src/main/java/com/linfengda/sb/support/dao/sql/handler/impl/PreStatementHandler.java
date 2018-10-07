package com.linfengda.sb.support.dao.sql.handler.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.linfengda.sb.support.dao.exception.DataAccessException;
import com.linfengda.sb.support.dao.sql.bo.PreStatementSql;
import com.linfengda.sb.support.dao.sql.handler.BaseSqlHandler;
import com.linfengda.sb.support.dao.sql.handler.SqlStatement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;

/**
 * @Description:
 * @Author: liugenhua
 * @Date created in 2018/3/24 19:03
 */
@Slf4j
public class PreStatementHandler extends BaseSqlHandler implements SqlStatement {

	private Connection conn;
	private PreparedStatement preStatement;
	private ResultSet result;
	private PreStatementSql preStatementSql;
	private DataSource dataSource;

	public PreStatementHandler(PreStatementSql sql, DataSource dataSource) {
		this.preStatementSql = sql;
		this.conn = DataSourceUtils.getConnection(dataSource);
		this.dataSource = dataSource;
	}

	/**
	 * 执行更新（增、删、改）
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int executeUpdate() throws SQLException {
		long start = System.currentTimeMillis();
		if(!DataSourceUtils.isConnectionTransactional(conn,dataSource)) {
			throw new DataAccessException("涉及数据更新，请开启事物");
		}
		this.preStatement = getPreparedStatement(conn,preStatementSql.getSql(),preStatementSql.getParams());
	    int count = this.preStatement.executeUpdate();
		long end = System.currentTimeMillis();
		log.debug("sql:{},use time:{}ms,affected record:{}",this.preStatementSql.getSql(),(end-start),count);
		return count;
	}

	/**
	 * 执行批量更新（增、删、改）
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int executeBatchSave() throws SQLException{
		long start = System.currentTimeMillis();
		if(!DataSourceUtils.isConnectionTransactional(conn,dataSource)) {
			throw new DataAccessException("涉及数据更新，请开启事物");
		}
		this.preStatement = getPreparedStatementForBatch(conn,preStatementSql.getSql(),preStatementSql.getBatchParams());
		preStatement.executeBatch();
		long end = System.currentTimeMillis();
		log.debug("sql:{},use time:{}ms",this.preStatementSql.getSql(),(end-start));
		return 0;
	}

	/**
	 * 执行查询
	 * @return
	 * @throws SQLException
	 */
	@Override
	public ResultSet executeQuery() throws SQLException {
		long start = System.currentTimeMillis();

		this.preStatement = getPreparedStatement(conn,preStatementSql.getSql(),preStatementSql.getParams());
		this.result = preStatement.executeQuery();

		long end = System.currentTimeMillis();
		log.debug("sql:{},use time:{}ms",this.preStatementSql.getSql(),(end-start));
		return this.result;
	}

	/**
	 * 执行存储过程
	 * @return
	 */
	@Override
	public Boolean executeProcedure() {
		if(!DataSourceUtils.isConnectionTransactional(conn,dataSource)) {
			throw new DataAccessException("涉及数据更新，请开启事物");
		}
		return null;
	}

	/**
	 * 释放资源
	 * @throws SQLException
	 */
	@Override
	public void close() throws SQLException {
		if(conn != null && !conn.isClosed()) {
			if (!DataSourceUtils.isConnectionTransactional(conn,dataSource)){
				conn.close();
			}
		}
		if(preStatement != null) {
			preStatement.close();
		}
		if(result != null) {
			result.close();
		}
	}
}
