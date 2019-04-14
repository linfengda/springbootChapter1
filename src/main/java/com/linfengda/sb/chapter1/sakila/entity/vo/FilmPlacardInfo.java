package com.linfengda.sb.chapter1.sakila.entity.vo;

import lombok.Data;

/**
 * 描述: 电影简介显示信息
 *
 * @author linfengda
 * @create 2018-08-13 22:06
 */
@Data
public class FilmPlacardInfo {

    private Integer filmId;
    private String title;
    private String description;
    private String releaseYear;
    private String languageName;
}
