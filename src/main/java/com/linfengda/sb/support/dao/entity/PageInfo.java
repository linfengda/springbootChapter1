package com.linfengda.sb.support.dao.entity;

import com.github.pagehelper.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页信息
 */
@Data
public class PageInfo<T> implements Serializable {

    private List<T> recoders;

    private long totalRecoder;

    private int pageNo;

    private int pageSize;

    private int totalPage;

    public PageInfo(Page<T> page){
        this.pageNo = page.getPageNum();
        this.pageSize = page.getPageSize();
        this.totalRecoder = page.getTotal();
        this.totalPage = page.getPages();
        this.recoders = page.getResult();
    }

    public PageInfo(){}

}
