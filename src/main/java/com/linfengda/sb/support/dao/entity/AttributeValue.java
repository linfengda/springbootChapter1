package com.linfengda.sb.support.dao.entity;

/**
 * @author liugenhua
 * @date 2018-03-20
 */
public class AttributeValue {

	private Object value;
	
	private String type;
	
	public AttributeValue(Object value,String type) {
		this.value = value;
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
