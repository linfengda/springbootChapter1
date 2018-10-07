package com.linfengda.sb.support.dao.entity.type;

/**
 * type enum
 * @author liugenhua
 *
 */
public enum BaseType {
	STRING("java.lang.String"),
	INT("int"),
	B_INT("java.lang.Integer"),
	FLOAT("float"),
	B_FLOAT("java.lang.Float"),
	DOUBLE("double"),
	B_DOUBLE("java.lang.Double"),
	BIG_DECIMAL("java.math.BigDecimal"),
	LONG("long"),
	B_LONG("java.lang.Long"),
	DATE("java.util.Date"),
	SQL_DATE("java.sql.Date"),
	SQL_DATE_TIME("java.sql.Timestamp"),
	BYTE("byte"),
	B_BYTE("java.lang.Byte"),
	BYTES("[B");

    public String value;
    
	 BaseType(String value) {
		this.value = value;
	}
	 
	 public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static BaseType getBaseType(String value) {
		for(BaseType type: values()) {
			if(type.getValue().equals(value)) {
				return type;
			}
		}
		return null;
	 }
}
