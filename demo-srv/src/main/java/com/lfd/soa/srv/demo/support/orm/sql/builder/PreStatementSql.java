package com.lfd.soa.srv.demo.support.orm.sql.builder;

import com.lfd.soa.srv.demo.support.orm.entity.AttributeValue;
import lombok.Data;

import java.util.List;

/**
 * 待执行的sql
 * @author linfengda
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
