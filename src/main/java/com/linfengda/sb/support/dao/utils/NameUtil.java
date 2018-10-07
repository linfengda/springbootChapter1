package com.linfengda.sb.support.dao.utils;

/**
 * @Description:
 * @Author: liugenhua
 * @Date created in 2018/5/24 19:03
 */
public class NameUtil {

    /**
     * 转化成驼峰命名
     * @param fieldName
     * @return
     */
    public static String convert(String fieldName) {
        char [] cs = fieldName.toCharArray();
        String targetName = "";
        for (char c : cs) {
            if(Character.isUpperCase(c)) {
                targetName += "_"+Character.toLowerCase(c);
            } else {
                targetName += c;
            }
        }

        return targetName;
    }
}
