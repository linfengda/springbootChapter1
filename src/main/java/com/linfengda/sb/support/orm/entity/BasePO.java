package com.linfengda.sb.support.orm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

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
	private Long createUser;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改人
	 */
	private Long updateUser;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 是否删除(0：未删除，1：已删除)
	 */
	private Boolean isDelete;

	/**
	 * 版本号
	 */
	private Integer version;

	/**
	 * 最后更新时间，BI团队使用
	 */
	private Date lastUpdateTime;

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
