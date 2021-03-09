package com.linfengda.sb.chapter1.common.bean;

import java.io.Serializable;

/**
 * 分页req
 *
 * @author linfengda
 * @date 2019-12-18 10:06
 */
public interface IBasePageParamReq extends Serializable {
    /**
     * 默认分页大小
     */
    int DEFAULT_PAGE_SIZE = 10;

    /**
     * 默认当前页码
     */
    int DEFAULT_PAGE = 1;

    /**
     * 获取当前页码
     *
     * @return 当前页码
     */
    int getPage();

    /**
     * 设置当前页码
     *
     * @param page 页码
     */
    void setPage(int page);

    /**
     * 获取分页大小
     *
     * @return 分页大小
     */
    int getPageSize();

    /**
     * 设置分页大小
     *
     * @param pageSize 分页大小
     */
    void setPageSize(int pageSize);
}
