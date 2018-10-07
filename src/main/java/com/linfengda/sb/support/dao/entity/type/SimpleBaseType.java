package com.linfengda.sb.support.dao.entity.type;

/**
 * type enum
 * @author liugenhua
 *
 */
public enum SimpleBaseType {
	STRING("string"),
	INT("int"),
	FLOAT("float"),
	DOUBLE("double"),
	BIG_DECIMAL("bigDecimal"),
	LONG("long"),
	DATE("date"),
	SQL_DATE("sqlDate"),
	SQL_DATE_TIME("sqlDateTime"),
	BYTE("byte"),
	BYTES("bytes");
	
    public String value;
    
	 SimpleBaseType(String value) {
		this.value = value;
	}
	 
	 public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static SimpleBaseType getBaseType(String value) {
		for(SimpleBaseType type: values()) {
			if(type.getValue().equals(value)) {
				return type;
			}
		}
		return null;
	 }
}
