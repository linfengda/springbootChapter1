package com.linfengda.sb.chapter1.serializeTest.entity;

import lombok.Data;

/**
 * 描述: 这是一只猪
 *
 * @author linfengda
 * @create 2018-09-12 16:08
 */
@Data
public class Pig {

    private long id;
    private String code;
    private String master;
    private double weight;
    private int growDays;
}
