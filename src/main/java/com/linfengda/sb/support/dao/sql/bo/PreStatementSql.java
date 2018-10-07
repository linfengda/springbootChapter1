package com.linfengda.sb.support.dao.sql.bo;

import com.linfengda.sb.support.dao.entity.AttributeValue;
import lombok.Data;

import java.util.List;

/**
 * 待执行的sql
 * @author liugenhua
 */
@Data
public class PreStatementSql {

	/**
	 * 带站位符的sql语句
	 */
	private String sql;
	/**
	 * 单条记录时对象参数
	 */
	private List<AttributeValue> params;
	/**
	 * 批量对象参数
	 */
	private List<List<AttributeValue>> batchParams;
}
