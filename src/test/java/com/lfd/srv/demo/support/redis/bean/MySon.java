package com.lfd.srv.demo.support.redis.bean;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 描述: 序列化的类型测试
 *
 * @author linfengda
 * @create 2018-09-12 15:54
 */
@Data
public class MySon extends YourPapa {

    private int f1;
    private long f2;
    private float f3;
    private double f4;
    private char f5;
    private boolean f6;
    private String f7;
    private Timestamp f8;
    private int[] f9;
    private long[] f10;
    private float[] f11;
    private double[] f12;
    private char[] f13;
    private boolean[] f14;
    private String[] f15;
    //private String UpperHeadField;
    //private String lowerHeadField;
    private Pig pig;
    private List<Pig> childPigs;
    private Map<String, Pig> kindPigs;

}
