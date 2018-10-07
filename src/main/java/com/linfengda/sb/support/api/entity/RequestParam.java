package com.linfengda.sb.support.api.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.linfengda.sb.support.api.exception.ParamParesException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 入口参数类
 *
 * @author linfengda
 * @create 2018-08-19 22:46
 */
public class RequestParam extends JSONObject {

    public List<Long> getArrayLong(String key) {
        JSONArray jsonArray = getJSONArray(key);
        if (jsonArray==null){
            return null;
        }
        List<Long> ls = new ArrayList<>(jsonArray.size());
        for (Object ob:jsonArray) {
            if(ob instanceof String){
                String v = (String)ob;
                if (StringUtils.isNotEmpty(v)){
                    ls.add(Long.valueOf(v));
                }
                continue;
            } else if (ob instanceof Long){
                ls.add((Long)ob);
                continue;
            } else if (ob instanceof Integer){
                Integer ov = (Integer)ob;
                ls.add(Long.valueOf(ov.toString()));
                continue;
            }else{
                throw new ParamParesException("参数类型不对");
            }
        }

        return  ls;
    }

    public List<Integer> getArrayInteger(String key) {
        JSONArray jsonArray = getJSONArray(key);
        if (jsonArray==null){
            return null;
        }
        List<Integer> ls = new ArrayList<>(jsonArray.size());
        for (Object ob:jsonArray) {
            String v = null;
            if (ob instanceof String){
                v = (String)ob;
                ls.add(Integer.valueOf(v.trim()));
                continue;
            }else if (ob instanceof Integer){
                ls.add((Integer)ob);
                continue;
            } else {
                throw new ParamParesException("参数类型不对");
            }
        }

        return  ls;
    }

    public List<String> getArrayString(String key) {
        JSONArray jsonArray = getJSONArray(key);
        if (jsonArray==null){
            return null;
        }
        List<String> ls = new ArrayList<>(jsonArray.size());
        for (Object ob:jsonArray) {
            if (ob instanceof String){
                ls.add((String)ob);
                continue;
            }
            if (ob instanceof Integer){
                ls.add(ob.toString());
                continue;
            }
            if (ob instanceof Double){
                ls.add(ob.toString());
                continue;
            }
            if (ob instanceof Float){
                continue;
            }
        }
        return  ls;
    }

    @Override
    public Integer getInteger(String key) {
        String v = super.getString(key);
        if (StringUtils.isEmpty(v)) {
            return null;
        }
        v = v.trim();
        if (StringUtils.isEmpty(v)) {
            return null;
        }
        try {
            return TypeUtils.castToInt(v);
        } catch (Exception e) {
            throw new ParamParesException("参数类型不对");
        }
    }

    @Override
    public Long getLong(String key) {
        String v = super.getString(key);
        if (StringUtils.isEmpty(v)) {
            return null;
        }
        v = v.trim();
        if (StringUtils.isEmpty(v)) {
            return null;
        }
        try {
            return TypeUtils.castToLong(v);
        } catch (Exception e) {
            throw new ParamParesException("参数类型不对");
        }
    }

    public <T> T getJavaBean(Class<T> clazz){
       return this.toJavaObject(clazz);
    }

    public String toString(){
        return  this.toJSONString();
    }
}
