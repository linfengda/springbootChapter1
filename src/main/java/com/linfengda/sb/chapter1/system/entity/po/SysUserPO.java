package com.linfengda.sb.chapter1.system.entity.po;

import com.linfengda.sb.support.dao.entity.BasePO;
import com.linfengda.sb.support.dao.tableAnnontation.Id;
import com.linfengda.sb.support.dao.tableAnnontation.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 描述: 系统用户
 *
 * @author linfengda
 * @create 2019-01-03 16:15
 */
@Table(name = "sys_user")
@Data
public class SysUserPO extends BasePO {
    /**
     * 主键ID
     */
    @Id
    private Long userId;
    /**
     * 用户编码
     */
    private String userCode;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户手机
     */
    private String phone;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户加工厂
     */
    private Long factoryId;
    /**
     * 用户二级组织
     */
    private Long productionTeamId;
    /**
     * 用户三级组织
     */
    private Long stationId;
    /**
     * 用户工种
     */
    private String workKind;
    /**
     * 用户微信
     */
    private String weixinId;
    /**
     * 用户salt
     */
    private String salt;
    /**
     * 用户类型
     */
    private Integer userType;
    /**
     * 用户状态
     */
    private Integer status;

    /** 状态类型 */
    public enum Status {
        /**
         * 启用
         */
        YES(0, "启用"),
        /**
         * 停用
         */
        NO(1, "停用");

        @Setter @Getter private Integer code;
        @Setter @Getter private String name;

        Status(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public static Status getType(Integer code) {
            for (Status status : values()) {
                if (status.code.equals(code)) {
                    return status;
                }
            }
            return null;
        }
    }
}