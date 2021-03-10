package com.lfd.srv.demo.bean.req;

import com.lfd.common.bean.BasePageParamReq;
import lombok.Data;

/**
 * 描述: 用户分页查询DTO
 *
 * @author linfengda
 * @create 2019-12-19 13:27
 */
@Data
public class UserPageQueryParamReq extends BasePageParamReq {
    /**
     * 用户状态
     */
    private Integer status;
    /**
     * 用户名称
     */
    private String userName;
}
