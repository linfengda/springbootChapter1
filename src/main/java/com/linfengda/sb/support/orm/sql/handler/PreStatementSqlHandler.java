package com.linfengda.sb.support.orm.sql.handler;

import com.linfengda.sb.support.orm.exception.DataAccessException;
import com.linfengda.sb.support.orm.sql.builder.PreStatementSql;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 描述: execute sql
 *
 * @author linfengda
 * @create 2019-04-12 13:25
 */
@Slf4j
public class PreStatementSqlHandler extends BaseSqlHandler {

	private DataSource dataSource;
	private Connection conn;
	private PreStatementSql preStatementSql;
	private PreparedStatement preStatement;
	private ResultSet result;

	public PreStatementSqlHandler(PreStatementSql sql, DataSource dataSource) {
		this.preStatementSql = sql;
		this.dataSource = dataSource;
		this.conn = DataSourceUtils.getConnection(dataSource);
	}

	/**
	 * 执行更新（增、删、改）
	 * @return
	 * @throws SQLException
	 */
	public int executeUpdate() throws SQLException {
		long start = System.currentTimeMillis();
		if(!DataSourceUtils.isConnectionTransactional(conn,dataSource)) {
			throw new DataAccessException("涉及数据更新，请开启事物");
		}
		this.preStatement = getPreparedStatement(conn,preStatementSql.getSql(),preStatementSql.getParams());
	    int count = this.preStatement.executeUpdate();
		long end = System.currentTimeMillis();
		log.info("sql:{},use time:{}ms,affected record:{}",this.preStatementSql.getSql(),(end-start),count);
		return count;
	}

	/**
	 * 执行批量更新（增、删、改）
	 * @return
	 * @throws SQLException
	 */
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
	public ResultSet executeQuery() throws SQLException {
		long start = System.currentTimeMillis();

		this.preStatement = getPreparedStatement(conn,preStatementSql.getSql(),preStatementSql.getParams());
		this.result = preStatement.executeQuery();

		long end = System.currentTimeMillis();
		log.debug("sql:{},use time:{}ms",this.preStatementSql.getSql(),(end-start));
		return this.result;
	}

	/**
	 * 释放资源
	 * @throws SQLException
	 */
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
