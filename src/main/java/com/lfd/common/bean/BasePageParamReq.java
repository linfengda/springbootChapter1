package com.lfd.common.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页req
 *
 * @author linfengda
 * @date 2021-03-08 14:04
 */
@Data
public abstract class BasePageParamReq implements IBasePageParamReq {
    private static final long serialVersionUID = 3347419754375205054L;

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码", example = "" + DEFAULT_PAGE)
    private int page = DEFAULT_PAGE;

    /**
     * 分页大小
     */
    @ApiModelProperty(value = "分页大小", example = "" + DEFAULT_PAGE_SIZE)
    private int pageSize = DEFAULT_PAGE_SIZE;
}
