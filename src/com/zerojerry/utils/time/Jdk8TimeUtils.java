package com.zerojerry.utils.time;

import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

/**
 * @Description JDK8的简单时间工具。
 * @Author ZeroJerry66
 * @Date 2019/7/12 16:35
 * @Version 1.0
 */
public class Jdk8TimeUtils {
    private Jdk8TimeUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");
    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");
    private static final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");
    private static final DateTimeFormatter SHORT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmss");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_CHINESIZE = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH点mm分");

    /**
     * 返回当前的日期 LocalDate
     *
     * @return
     */
    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now();
    }

    /**
     * 返回当前时间 LocalTime
     *
     * @return
     */
    public static LocalTime getCurrentLocalTime() {
        return LocalTime.now();
    }

    /**
     * 返回当前日期时间 LocalDateTime
     *
     * @return
     */
    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 返回当前日期字符串。（yyyy-MM-dd）
     *
     * @return
     */
    public static String getCurrentDateStr() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    /**
     * 返回当前年月日。（yyMMdd）
     *
     * @return
     */
    public static String getCurrentShortDateStr() {
        return LocalDate.now().format(SHORT_DATE_FORMATTER);
    }

    /**
     * 返回当前年、月字符串（yyyyMM）
     *
     * @return
     */
    public static String getCurrentMonthStr() {
        return LocalDate.now().format(MONTH_FORMATTER);
    }

    /**
     * 返回当前年、月、日字符串（yyyy-MM-dd HH:mm:ss）
     *
     * @return
     */
    public static String getCurrentDateTimeStr() {
        return LocalDateTime.now().format(DATETIME_FORMATTER);
    }

    /**
     * yyMMddHHmmss
     *
     * @return
     */
    public static String getCurrentShortDateTimeStr() {
        return LocalDateTime.now().format(SHORT_DATETIME_FORMATTER);
    }

    /**
     * HHmmss
     *
     * @return
     */
    public static String getCurrentTimeStr() {
        return LocalTime.now().format(TIME_FORMATTER);
    }

    public static String getCurrentDateStr(String pattern) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getCurrentDateTimeStr(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getCurrentTimeStr(String pattern) {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 字符串转时间（LocalDate）。
     *
     * @param dateStr 时间字符串
     * @param pattern 模式字符串
     * @return
     */
    public static LocalDate parseLocalDate(String dateStr, String pattern) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 字符串转时间（LocalDateTime）。
     *
     * @param dateTimeStr 时间字符串
     * @param pattern     模式字符串
     * @return
     */
    public static LocalDateTime parseLocalDateTime(String dateTimeStr, String pattern) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 字符串转时间（LocalDateTime）。自己判断[模式字符串]是yyyy-MM-dd或yyyy-MM-dd HH:mm:ss。
     *
     * @param dateTime 时间字符串
     * @return
     */
    public static LocalDateTime convertStr2Ldt(String dateTime) {
        LocalDateTime ldt = null;
        if (StringUtils.length(dateTime) == 10 && StringUtils.contains(dateTime, "-")) {
            // 如果字符串类型是 yyyy-MM-dd
            ldt = LocalDateTime.parse(dateTime, DATE_FORMATTER);
        } else if (StringUtils.length(dateTime) == 19 && StringUtils.contains(dateTime, "-")
                && StringUtils.contains(dateTime, ":")) {
            // 如果字符串类型是 yyyy-MM-dd HH:mm:ss
            ldt = LocalDateTime.parse(dateTime, DATETIME_FORMATTER);
        }
        return ldt;
    }

    /**
     * 字符串转时间（LocalTime）
     *
     * @param timeStr 时间字符串
     * @param pattern 模式字符串
     * @return
     */
    public static LocalTime parseLocalTime(String timeStr, String pattern) {
        return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatLocalDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatLocalDateTime(LocalDateTime datetime, String pattern) {
        return datetime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatLocalTime(LocalTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate parseLocalDate(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    public static LocalDateTime parseLocalDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER);
    }

    /**
     * 字符串转时间（LocalTime）
     *
     * @param timeStr HHmmss
     * @return
     */
    public static LocalTime parseLocalTime(String timeStr) {
        return LocalTime.parse(timeStr, TIME_FORMATTER);
    }

    public static String formatLocalDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    /**
     * LocalDateTime转字符串
     * yyyy-MM-dd HH:mm:ss
     *
     * @param datetime
     * @return
     */
    public static String formatLocalDateTime(LocalDateTime datetime) {
        return datetime.format(DATETIME_FORMATTER);
    }

    /**
     * LocalDateTime转字符串
     * yyyy年MM月dd日 HH:mm分
     *
     * @param datetime
     * @return
     */
    public static String formatLocalDateTimeChinesize(LocalDateTime datetime) {
        return datetime.format(DATETIME_CHINESIZE);
    }

    public static String formatLocalTime(LocalTime time) {
        return time.format(TIME_FORMATTER);
    }

    /**
     * 日期相隔天数
     * 注意：使用Period.between（）方法来获取，相差天数、相差月数的时候，只能计算同月的天数、同年的月数，不能计算隔月的天数以及隔年的月数！！！
     *
     * @param startDateInclusive
     * @param endDateExclusive
     * @return
     */
    public static int periodDays(LocalDate startDateInclusive, LocalDate endDateExclusive) {
        return Period.between(startDateInclusive, endDateExclusive).getDays();
    }

    /**
     * 自定义的 获取日期相隔天数。(注意：会出现负数，标识开始时间小于结束时间多少天)
     *
     * @param startDateInclusive
     * @param endDateExclusive
     * @return
     */
    public static Long customPeriodDays(LocalDate startDateInclusive, LocalDate endDateExclusive) {
        return startDateInclusive.toEpochDay() - endDateExclusive.toEpochDay();
    }

    /**
     * 两个LocalDate的相差月数。(注意：会出现负数，标识开始时间小于结束时间多少月)
     *
     * @param startDateInclusive
     * @param endDateExclusive
     * @return
     */
    public static Long customPeriodMounths(LocalDate startDateInclusive, LocalDate endDateExclusive) {
        startDateInclusive.until(endDateExclusive, ChronoUnit.MONTHS);
        return startDateInclusive.toEpochDay() - endDateExclusive.toEpochDay();
    }

    /**
     * 日期相隔小时
     *
     * @param startInclusive
     * @param endExclusive
     * @return
     */
    public static long durationHours(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toHours();
    }

    /**
     * 日期相隔分钟
     *
     * @param startInclusive
     * @param endExclusive
     * @return
     */
    public static long durationMinutes(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMinutes();
    }

    /**
     * 日期相隔毫秒数
     *
     * @param startInclusive
     * @param endExclusive
     * @return
     */
    public static long durationMillis(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMillis();
    }

    /**
     * 是否当天
     *
     * @param date
     * @return
     */
    public static boolean isToday(LocalDate date) {
        return getCurrentLocalDate().equals(date);
    }

    /**
     * 获取当前时区的纪元毫秒值。
     *
     * @param dateTime
     * @return
     */
    public static Long toEpochMilli(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    //获取指定日期的秒
    public static Long getSecondsByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }


}
