package com.linfengda.sb.support.orm.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 分页查询结果
 * @author: linfengda
 * @date: 2020-11-30 23:18
 */
@Data
public class PageResult<T> implements Serializable {

    private List<T> recorders;

    private int pageNo;

    private int pageSize;

    private long totalRecord;

    private int totalPage;
}
