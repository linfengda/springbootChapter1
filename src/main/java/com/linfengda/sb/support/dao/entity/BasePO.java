package com.linfengda.sb.support.dao.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;

/**
 * 表实体基础类
 * @author linfengda
 *
 */
@Data
public class BasePO {

	/** 创建人*/
	private Long createUser;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Timestamp createTime;

	/** 更新人*/
	private Long updateUser;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Timestamp updateTime;

	/** 版本 */
	private Integer version;
    /** 是否删除 */
	private Integer isDelete;

	/**
	 * 删除字段枚举
	 */
	@AllArgsConstructor
	@Getter
	public enum Delete {
		NORMAL(0, "正常"),
		DELETED(1, "删除");
		private final Integer code;
		private final String name;

		public static Delete getType(Integer state) {
			for (Delete delete : values()) {
				if (delete.getCode().equals(state)) {
					return delete;
				}
			}
			return null;
		}
	}
}
