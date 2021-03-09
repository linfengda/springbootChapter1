package com.linfengda.sb.support.orm.auto;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.linfengda.sb.support.orm.annontation.Id;
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
public abstract class BaseEntity {
	@Id
	private Integer id;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建人UID
	 */
	private String createUid;

	/**
	 * 创建人名称(中文)
	 */
	private String createUser;

	/**
	 * 更新人UID
	 */
	private String updateUid;

	/**
	 * 更新人名称(中文)
	 */
	private String updateUser;

	/**
	 * 软删除标记
	 */
	@TableLogic
	private Integer deleteTag;

	/**
	 * 乐观锁
	 */
	@Version
	private Integer version;


	public void onCreate() {
		createUid = UserHolder.getCurrentUid();
		createUser = UserHolder.getCurrentUName();
		createTime = new Timestamp(System.currentTimeMillis());
		version = 1;
	}

	public void onUpdate() {
		updateUid = UserHolder.getCurrentUid();
		updateUser = UserHolder.getCurrentUName();
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
