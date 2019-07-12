package com.linfengda.sb.chapter1.sakila.entity.po;

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
public class FilmPO {

    private Integer filmId;

    private String title;

    private String description;

    private String releaseYear;

    private Integer languageId;

    private Integer originalLanguageId;

    private Integer rentalDuration;

    private Double rentalRate;

    private Integer length;

    private Double replacementCost;

    private String rating;

    private String specialFeatures;

    private Timestamp lastUpdate;
}
