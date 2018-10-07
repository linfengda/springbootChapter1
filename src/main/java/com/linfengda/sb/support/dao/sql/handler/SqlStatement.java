package com.linfengda.sb.support.dao.sql.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author liucn
 *
 */
public interface SqlStatement {

	/**
	 * 执行更新（增、删、改）
	 * @return
	 */
	int executeUpdate() throws SQLException;

	/**
	 * 批量更新(新增)
	 * @return
	 * @throws SQLException
	 */
	int executeBatchSave() throws SQLException;
	
	/**
	 * 执行查询
	 * @return
	 */
	ResultSet executeQuery() throws SQLException;
	
	/**
	 * 执行存储过程
	 * @return
	 */
	Boolean executeProcedure();
	
	/**
	 * 释放资源
	 * @throws SQLException
	 */
	void close() throws SQLException;
}
