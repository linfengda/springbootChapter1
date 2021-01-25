package com.linfengda.sb.support.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 描述: 字符串工具类
 *
 * @author linfengda
 * @create 2018-09-12 13:40
 */
public class StringUtil {

    private static final char[] DIGITS = {
            '0', '1', '2', '3', '4', '5', '6','7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f','g','h','j','k','l','z','x','c','v','b','n','m','q','w','r','t','y','u','i','o','@','#','~','*',
            'A', 'B', 'C', 'D', 'E', 'F','G','H','J','K','L','Z','X','C','V','B','N','M','Q','W','R','T','Y','U','I','O','$'
    };

    private static final char[] NUM_DIGITS = {
            '0', '1', '2', '3', '4', '5', '6','7', '8', '9'
    };

    public static final String EMPTY = "";
    public static final String EMPTY_SPACE = " ";
    public static final String SLASH = "/";

    public static String join(String separator, Object... objects){
        if(objects == null || objects.length == 0){
            return null;
        }
        ArrayList joinList = new ArrayList<>(Arrays.asList(objects));
        return join(separator,joinList);
    }

    public static String joinWithoutEmpty(String separator, Object... objects){
        if(objects == null || objects.length == 0){
            return null;
        }
        ArrayList joinList = new ArrayList<>(Arrays.asList(objects));
        return joinWithoutEmpty(separator,joinList);
    }

    public static String join(String separator, Collection<?> coll){
        if(CollectionUtils.isEmpty(coll)){
            return null;
        }
        if(StringUtils.isEmpty(separator)){
            separator = SLASH;
        }
        ArrayList joinList = new ArrayList<>();
        for (Object object :coll) {
            if(object != null){
                if(object instanceof BigDecimal){
                    joinList.add(((BigDecimal) object).setScale(2, BigDecimal.ROUND_HALF_UP));
                }else{
                    joinList.add(object);
                }
            }
        }
        return StringUtils.join(joinList,separator);
    }

    public static String joinWithoutEmpty(String separator, Collection<?> coll){
        if(CollectionUtils.isEmpty(coll)){
            return null;
        }
        if(StringUtils.isEmpty(separator)){
            separator = SLASH;
        }
        ArrayList joinList = new ArrayList<>();
        for (Object object :coll) {
            if(object != null){
                if(object instanceof BigDecimal){
                    joinList.add(((BigDecimal) object).setScale(2, BigDecimal.ROUND_HALF_UP));
                }else if(object instanceof String){
                    if (StringUtils.isNotBlank((String) object)) {
                        joinList.add(object);
                    }
                }else{
                    joinList.add(object);
                }
            }
        }
        return StringUtils.join(joinList,separator);
    }

    public static String getRandomStr(int len){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i < len; i++){
            sb.append(DIGITS[random.nextInt(66)]);
        }
        return sb.toString();
    }

    public static String getRandomNum(int len){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i < len; i++){
            sb.append(NUM_DIGITS[random.nextInt(9)]);
        }
        return sb.toString();
    }

    public static List<String> string2List(String str, String separatorChar) {
        if (StringUtils.isBlank(separatorChar)) {
            return null;
        }
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String[] array = str.split(separatorChar);
        List<String> list = new ArrayList<>(array.length);
        for (String value : array) {
            list.add(value);
        }
        return list;
    }

    public static List<Integer> string2IntList(String str, String separatorChar) {
        if (StringUtils.isBlank(separatorChar)) {
            return null;
        }
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String[] array = str.split(separatorChar);
        List<Integer> list = new ArrayList<>(array.length);
        for (String value : array) {
            list.add(Integer.valueOf(value));
        }
        return list;
    }

}
