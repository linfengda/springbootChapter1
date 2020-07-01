package com.linfengda.sb.chapter1.common.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 时间工具类
 * @author: linfengda
 * @date: 2020-05-14 15:07
 */
public class TimeUtil {
    public static class FormatCache {
        /**
         * 缓存对象内部日期时间格式化对象
         */
        public final Map<String,SimpleDateFormat> TIME_FORMAT_MAP = new HashMap<>(8);
    }
    private final static ThreadLocal<FormatCache> DATE_TIME_FORMAT_THREAD_LOCAL = new ThreadLocal();

    /**
     * 获取线程安全的时间格式化对象
     * @param pattern   时间格式
     * @return          线程安全的时间格式化对象
     */
    private static SimpleDateFormat getSafeSimpleDateFormat(String pattern){
        SimpleDateFormat simpleDateFormat;
        FormatCache formatCache = DATE_TIME_FORMAT_THREAD_LOCAL.get();
        if (formatCache == null){
            simpleDateFormat = new SimpleDateFormat(pattern);
            formatCache = new FormatCache();
            formatCache.TIME_FORMAT_MAP.put(pattern,simpleDateFormat);
            DATE_TIME_FORMAT_THREAD_LOCAL.set(formatCache);
        }else {
            simpleDateFormat = formatCache.TIME_FORMAT_MAP.get(pattern);
            if(simpleDateFormat == null){
                simpleDateFormat = new SimpleDateFormat(pattern);
                formatCache.TIME_FORMAT_MAP.put(pattern,simpleDateFormat);
            }
        }
        return simpleDateFormat;
    }

    /**
     * 计算2个时间之间的差值
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param type      差值类型
     * @return
     */
    public static Long calculateTimeMinus(Timestamp startTime, Timestamp endTime, String type) {
        if (null == startTime || null == endTime) {
            return 0L;
        }
        switch (type) {
            case "毫秒": return endTime.getTime()-startTime.getTime();
            default: break;
        }
        return 0L;
    }

    /**
     * 获取当前时间前移/后移时间
     * @param time          时间
     * @param expandMills   扩大区间，负数前移，正数后移
     */
    public static Timestamp getExpandTime(Timestamp time, long expandMills) {
        Timestamp expandTime = new Timestamp(time.getTime() + expandMills);
        return expandTime;
    }

    /**
     * 判断时间是否为空
     * @param time  时间
     * @return      true：是，false：否
     */
    public static boolean isNullTime(Long time) {
        return null == time || new Long(0).equals(time);
    }

    /**
     * 转换为Timestamp
     * @param time      字符串格式时间
     * @param pattern   格式
     * @return          时间戳
     * @throws ParseException
     */
    public static Timestamp parseToTimestamp(String time, String pattern) throws ParseException {
        if (StringUtils.isBlank(time) || StringUtils.isBlank(pattern)) {
            return null;
        }
        return new Timestamp(getSafeSimpleDateFormat(pattern).parse(time).getTime());
    }

    /**
     * 转换为Date
     * @param time      字符串格式时间
     * @param pattern   格式
     * @return          时间戳
     * @throws ParseException
     */
    public static Date parseToDate(String time, String pattern) throws ParseException {
        if (StringUtils.isBlank(time) || StringUtils.isBlank(pattern)) {
            return null;
        }
        return getSafeSimpleDateFormat(pattern).parse(time);
    }

    /**
     * 转换为时间戳
     * @param time      字符串格式时间
     * @param pattern   格式
     * @return          时间戳
     * @throws ParseException
     */
    public static Long parseToTime(String time, String pattern) throws ParseException {
        if (StringUtils.isBlank(time) || StringUtils.isBlank(pattern)) {
            return null;
        }
        return getSafeSimpleDateFormat(pattern).parse(time).getTime();
    }

    /**
     * 判断日期格式是否正确
     * @param date
     * @param format
     * @return boolean
     */
    public static boolean determineTimeFormat(String date, String format) {
        if (null == date && StringUtils.isEmpty(format)) {
            return false;
        }
        try {
            getSafeSimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static String format(Timestamp timestamp, String pattern){
        if(null == timestamp || StringUtils.isEmpty(pattern)){
            return null;
        }
        return getSafeSimpleDateFormat(pattern).format(timestamp);
    }

    public static String format(Date date, String pattern){
        if(null == date || StringUtils.isEmpty(pattern)){
            return null;
        }
        return getSafeSimpleDateFormat(pattern).format(date);
    }

    public static String format(Long time, String pattern){
        if(null == time || StringUtils.isEmpty(pattern)){
            return null;
        }
        return getSafeSimpleDateFormat(pattern).format(time);
    }

    /**
     * 计算两个日期间隔的天数
     * @param decrease 较大的时间
     * @param minus 较小的时间
     * @return
     */
    public static long subtractionDay(Date decrease, Date minus) {
        if (null == decrease || null == minus) {
            return 0L;
        }
        LocalDateTime startLocalDateTime = LocalDateTime.ofInstant(decrease.toInstant(), ZoneId.systemDefault());
        LocalDateTime endLocalDateTime = LocalDateTime.ofInstant(minus.toInstant(), ZoneId.systemDefault());
        long days = ChronoUnit.DAYS.between(startLocalDateTime.toLocalDate(), endLocalDateTime.toLocalDate());
        return days;
    }

    /**
     * 计算两个日期间隔的天数
     * @param decrease 较大的时间
     * @param minus 较小的时间
     * @return
     */
    public static long subtractionDay(Timestamp decrease, Timestamp minus) {
        if (null == decrease || null == minus) {
            return 0L;
        }
        LocalDateTime startLocalDateTime =  decrease.toLocalDateTime();
        LocalDateTime endLocalDateTime = minus.toLocalDateTime();
        long days = ChronoUnit.DAYS.between(startLocalDateTime.toLocalDate(), endLocalDateTime.toLocalDate());
        return days;
    }

    /**
     * 计算两个时间相差的秒数
     * @param decrease 较大的时间
     * @param minus 较小的时间
     * @return
     */
    public static long subtractionSecond(Timestamp decrease, Timestamp minus){
        if (null == decrease || null == minus) {
            return 0L;
        }
        return subtractionSecond(decrease.getTime(), minus.getTime());
    }

    /**
     * 计算两个时间相差的秒数
     * @param decrease 较大的时间
     * @param minus 较小的时间
     * @return
     */
    public static long subtractionSecond(Date decrease, Date minus){
        if (null == decrease || null == minus) {
            return 0L;
        }
        return subtractionSecond(decrease.getTime(), minus.getTime());
    }

    /**
     * 计算两个时间相差的秒数
     * @param decrease 较大的时间
     * @param minus 较小的时间
     * @return
     */
    public static long subtractionSecond(Long decrease, Long minus){
        if (null == decrease || null == minus) {
            return 0L;
        }
        Long hours = decrease - minus;
        long l = hours / 1000;
        return l;
    }

    /**
     * 获取n天之前
     * @param days  n天
     * @return      n天之前的时间
     */
    public static Timestamp getBeforeNDaysTimestamp(int days) {
        Long time = getBeforeNDaysTime(days);
        Timestamp date = new Timestamp(time);
        return date;
    }

    /**
     * 获取n天之前
     * @param days  n天
     * @return      n天之前的时间
     */
    public static Date getBeforeNDaysDate(int days) {
        Long time = getBeforeNDaysTime(days);
        Date date = new Date(time);
        return date;
    }

    /**
     * 获取n天之前
     * @param days  n天
     * @return      n天之前的时间
     */
    public static Long getBeforeNDaysTime(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, days);
        return calendar.getTimeInMillis();
    }

    /**
     * 判断当前日期是否是当天
     * @param date
     * @return
     */
    public static boolean isToday(Timestamp date){
        if (null == date) {
            return false;
        }
        return isSameDay(date.getTime(), System.currentTimeMillis());
    }

    /**
     * 判断当前日期是否是当天
     * @param date
     * @return
     */
    public static boolean isToday(Date date){
        if (null == date) {
            return false;
        }
        return isSameDay(date.getTime(), System.currentTimeMillis());
    }

    /**
     * 判断当前日期是否是当天
     * @param date
     * @return
     */
    public static boolean isToday(Long date){
        if (null == date) {
            return false;
        }
        return isSameDay(date, System.currentTimeMillis());
    }

    /**
     * 判断两个日期是否属于同一天
     * @param firstTime
     * @param secondTime
     * @return
     */
    public static boolean isSameDay(Timestamp firstTime, Timestamp secondTime){
        if (null == firstTime || null == secondTime) {
            return false;
        }
        return isSameDay(firstTime.getTime(), secondTime.getTime());
    }

    /**
     * 判断两个日期是否属于同一天
     * @param firstTime
     * @param secondTime
     * @return
     */
    public static boolean isSameDay(Date firstTime, Date secondTime){
        if (null == firstTime || null == secondTime) {
            return false;
        }
        return isSameDay(firstTime.getTime(), secondTime.getTime());
    }

    /**
     * 判断两个日期是否属于同一天
     * @param firstTime
     * @param secondTime
     * @return
     */
    public static boolean isSameDay(Long firstTime, Long secondTime){
        if (null == firstTime || null == secondTime) {
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(firstTime);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(firstTime);
        String date1 = String.valueOf(cal1.get(Calendar.YEAR)) + cal1.get(Calendar.MONDAY) + cal1.get(Calendar.DAY_OF_MONTH);
        String date2 = String.valueOf(cal2.get(Calendar.YEAR)) + cal2.get(Calendar.MONDAY) + cal2.get(Calendar.DAY_OF_MONTH);
        return date1.equals(date2);
    }
}