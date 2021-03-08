package com.linfengda.sb.chapter1.common.bean.po;

import com.linfengda.sb.support.orm.entity.BaseEntityAware;
import com.linfengda.sb.support.orm.utils.UserUtil;
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
public abstract class BaseIncrementEntity implements BaseEntityAware {
	/**
	 * 创建人id
	 */
	private Integer createUser;
	/**
	 * 创建人
	 */
	private String createUserName;
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
	/**
	 * 修改人id
	 */
	private Integer updateUser;
	/**
	 * 修改人
	 */
	private Integer updateUserName;
	/**
	 * 修改时间
	 */
	private Timestamp updateTime;
	/**
	 * 是否删除 {@link Deleted}
	 */
	private Integer deleted;
	/**
	 * 版本号
	 */
	private Integer version;


	@Override
	public void onCreate() {
		createUser = UserUtil.getCurrentUserId();
		createTime = new Timestamp(System.currentTimeMillis());
		updateUser = UserUtil.getCurrentUserId();
		updateTime = new Timestamp(System.currentTimeMillis());
		version = 1;
	}

	@Override
	public void onUpdate() {
		updateUser = UserUtil.getCurrentUserId();
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
	public enum Deleted {
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

		public static Deleted getType(Integer state) {
			for (Deleted deleted : values()) {
				if (deleted.getCode().equals(state)) {
					return deleted;
				}
			}
			return null;
		}
	}

}
