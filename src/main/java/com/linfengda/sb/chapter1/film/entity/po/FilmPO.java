package com.linfengda.sb.chapter1.film.entity.po;

import com.linfengda.sb.support.dao.entity.BasePO;
import com.linfengda.sb.support.dao.tableAnnontation.Id;
import com.linfengda.sb.support.dao.tableAnnontation.Table;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 描述: 电影PO
 *
 * @author linfengda
 * @create 2018-09-12 14:15
 */
@Data
@Table(name = "film")
public class FilmPO extends BasePO {
    /**
     * 电影ID
     */
    @Id
    private Long filmId;
    /**
     * 电影标题
     */
    private String title;
    /**
     * 电影简介
     */
    private String description;
    /**
     * 发布日期
     */
    private Timestamp releaseYear;
    /**
     * 语言
     */
    private String language;
    /**
     * 长度
     */
    private Integer length;
}
