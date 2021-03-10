package com.lfd.soa.srv.demo.support.mybatis.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述: 类型枚举
 *
 * @author linfengda
 * @create 2019-04-12 13:25
 */
@Getter
@AllArgsConstructor
public enum BaseType {
	/**
	 * java.lang.String
	 */
	STRING("java.lang.String"),
	/**
	 * int
	 */
	INT("int"),
	/**
	 * java.lang.Integer
	 */
	B_INT("java.lang.Integer"),
	/**
	 * float
	 */
	FLOAT("float"),
	/**
	 * java.lang.Float
	 */
	B_FLOAT("java.lang.Float"),
	/**
	 * double
	 */
	DOUBLE("double"),
	/**
	 * java.lang.Double
	 */
	B_DOUBLE("java.lang.Double"),
	/**
	 * java.math.BigDecimal
	 */
	BIG_DECIMAL("java.math.BigDecimal"),
	/**
	 * long
	 */
	LONG("long"),
	/**
	 * java.lang.Long
	 */
	B_LONG("java.lang.Long"),
	/**
	 * java.util.Date
	 */
	DATE("java.util.Date"),
	/**
	 * java.sql.Date
	 */
	SQL_DATE("java.sql.Date"),
	/**
	 * java.sql.Timestamp
	 */
	SQL_TIMESTAMP("java.sql.Timestamp"),
	/**
	 * byte
	 */
	BYTE("byte"),
	/**
	 * java.lang.Byte
	 */
	B_BYTE("java.lang.Byte"),
	/**
	 * java.lang.Byte
	 */
	B_BYTES("[Ljava.lang.Byte;"),
	/**
	 * boolean
	 */
	BOOLEAN("boolean"),
	/**
	 * java.lang.Boolean
	 */
	B_BOOLEAN("java.lang.Boolean");

    private String value;

	private static final Map<String,BaseType> BASE_TYPE_MAP = new HashMap<>();

	static {
		for (BaseType type: values()) {
			BASE_TYPE_MAP.put(type.getValue(),type);
		}
	}
    
	 public static boolean isBaseDataType(String type){
		return BASE_TYPE_MAP.containsKey(type);
	 }
}
