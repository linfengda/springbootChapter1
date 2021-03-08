package com.linfengda.sb.support.orm.entity;

import com.linfengda.sb.support.orm.utils.UserUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 表实体基础类
 * @author linfengda
 *
 */
@Data
public abstract class BaseEntity implements BaseEntityAware {
	/**
	 * 创建人uid
	 */
	private Integer createUid;
	/**
	 * 创建人
	 */
	private String createUserName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改人uid
	 */
	private Integer updateUid;
	/**
	 * 修改人
	 */
	private String updateUserName;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 是否删除 {@link DeleteTag}
	 */
	private Integer deleteTag;
	/**
	 * 版本号
	 */
	private Integer version;


	@Override
	public void onCreate() {
		createUid = UserUtil.getCurrentUid();
		createUserName = UserUtil.getCurrentUName();
		createTime = new Timestamp(System.currentTimeMillis());
		version = 1;
	}

	@Override
	public void onUpdate() {
		updateUid = UserUtil.getCurrentUid();
		updateUserName = UserUtil.getCurrentUName();
		updateTime = new Timestamp(System.currentTimeMillis());
		if (version == null){
			version = 1;
		}else {
			version = version+1;
		}
	}

	/**
	 * 删除字段枚举
	 */
	@AllArgsConstructor
	@Getter
	public enum DeleteTag {
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

		public static DeleteTag getType(Integer state) {
			for (DeleteTag deleteTag : values()) {
				if (deleteTag.getCode().equals(state)) {
					return deleteTag;
				}
			}
			return null;
		}
	}

}
