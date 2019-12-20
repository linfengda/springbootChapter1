package com.linfengda.sb.chapter1.common.api.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 描述: 分页返回信息
 *
 * @author linfengda
 * @create 2018-08-19 22:51
 */
@Data
public class PageResult extends Result {
    //总记录数
    @Setter @Getter private long totalCount;
    //总页数
    @Setter @Getter private long totalPage;

    public PageResult(long totalCount, long totalPage, Object data){
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.setInfo(data);
    }

}
