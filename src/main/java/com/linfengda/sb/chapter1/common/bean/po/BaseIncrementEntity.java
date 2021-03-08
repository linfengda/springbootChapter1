package com.linfengda.sb.chapter1.common.bean.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author linfengda
 * @date 2021-03-08 18:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseIncrementEntity<T> extends Model<BaseIncrementEntity<T>> {

    @TableId(value = "id", type = IdType.AUTO)
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
    private Boolean deleteTag;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;
}
