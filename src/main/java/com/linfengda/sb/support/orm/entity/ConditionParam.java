package com.linfengda.sb.support.orm.entity;

import com.linfengda.sb.support.orm.type.SortType;
import com.linfengda.sb.support.orm.utils.ClassUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 条件参数类
 * @author linfengda
 */
public class ConditionParam implements Serializable{

    /**
     * and 关键字
     */
    private static final String AND = " AND ";

    private static final String SEMICOLON = ";";

    /**
     * OR 关键字
     */
    private static final String OR = " OR ";

    /**
     * like 关键字
     */
    private static final  String LIKE = " LIKE ";

    /**
     * 排序关键字
     */
    private static final String ORDER = " ORDER BY";

    /**
     * 分页关键字
     */
    private static final String LIMIT = " LIMIT ";

    /**
     * 条件语句
     */
    private String conditionSql = " WHERE 1=1";

    /**
     * 排序语句
     */
    private String orderBy = ORDER;

    /**
     * 条件参数值
     */
    private List<AttributeValue> params = new ArrayList<AttributeValue>();

    /**默认页码*/
    private int pageNo = 1;

    /**默认每页数量*/
    private  int pageSize = 100;

    public ConditionParam add(String name,Object value){
        this.conditionSql += AND+ ClassUtil.convert(name)+"=?";
        this.params.add(new AttributeValue(value, ClassUtil.convertType(value.getClass())));
        return this;
    }

    public ConditionParam or(String name,Object value){
        this.conditionSql += OR+ClassUtil.convert(name)+"=?";
        this.params.add(new AttributeValue(value, ClassUtil.convertType(value.getClass())));
        return this;
    }

    public ConditionParam like(String name,Object value){
        this.conditionSql += AND+ClassUtil.convert(name)+LIKE+"?";
        this.params.add(new AttributeValue(value, ClassUtil.convertType(value.getClass())));
        return this;
    }

    public void orderBy(String name, SortType sortType){
        if (orderBy.equals(ORDER)){
            orderBy += " "+ClassUtil.convert(name)+" "+sortType.getValue();
        } else {
            orderBy += ","+ ClassUtil.convert(name)+" "+sortType.getValue();
        }
    }

    public String getPageConditionSql() {
        if (!orderBy.equals(ORDER)){
            return this.conditionSql+orderBy+ LIMIT +(pageNo-1)*pageSize+","+pageSize+SEMICOLON;
        }
        return conditionSql + LIMIT +(pageNo-1)*pageSize+","+pageSize+SEMICOLON;
    }

    public String getNotPageConditionSql(){
        if (!orderBy.equals(ORDER)){
            return this.conditionSql+orderBy+SEMICOLON;
        }
        return conditionSql;
    }

    public String getConditionSql() {
        return conditionSql+SEMICOLON;
    }

    public List<AttributeValue> getParams() {
        return params;
    }

    public int getPageNo() {
        return pageNo;
    }

    public ConditionParam setPageNo(int pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public ConditionParam setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
