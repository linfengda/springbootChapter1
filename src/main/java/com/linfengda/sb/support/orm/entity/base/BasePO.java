package com.linfengda.sb.support.orm.entity.base;

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
	/**
	 * 创建人
	 */
	private Integer createUser;
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
	/**
	 * 修改人
	 */
	private Integer updateUser;
	/**
	 * 修改时间
	 */
	private Timestamp updateTime;
	/**
	 * 是否删除 {@link Delete}
	 */
	private Integer delete;
	/**
	 * 版本号
	 */
	private Integer version;

	/**
	 * 删除字段枚举
	 */
	@AllArgsConstructor
	@Getter
	public enum Delete {
		/**
		 * 0：正常
		 */
		NORMAL(0, "正常"),
		/**
		 * 1：删除
		 */
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
