package com.zerojerry.utils.time20210201;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static com.douyu.wsd.hr.admin.human.client.util.time.LocalDateTimeUtils.ZONE_ID_SH;

public class LocalDateUtils {
    private LocalDateUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 返回当前日期字符串。（yyyy-MM-dd）
     *
     * @return
     */
    public static String getCurrentDateStr() {
        return LocalDate.now().format(com.douyu.wsd.hr.admin.human.client.util.time.LocalDateTimeUtils.DATE_FORMATTER);
    }

    /**
     * 返回当前年、月字符串（yyyyMM）
     *
     * @return
     */
    public static String getCurrentMonthStr() {
        return LocalDate.now().format(com.douyu.wsd.hr.admin.human.client.util.time.LocalDateTimeUtils.MONTH_FORMATTER);
    }

    /**
     * 返回当前年月日。（yyMMdd）
     *
     * @return
     */
    public static String getCurrentShortDateStr() {
        return LocalDate.now().format(com.douyu.wsd.hr.admin.human.client.util.time.LocalDateTimeUtils.SHORT_DATE_FORMATTER);
    }

    public static String getCurrentDateStr(String pattern) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
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

    public static String formatLocalDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate parseLocalDate(String dateStr) {
        return LocalDate.parse(dateStr, com.douyu.wsd.hr.admin.human.client.util.time.LocalDateTimeUtils.DATE_FORMATTER);
    }

    /**
     * 毫秒级Unix时间戳转LocalDate
     *
     * @param unixTimestamp 毫秒级Unix时间戳
     * @return LocalDate
     */
    public static LocalDate unix2LocalDate(Long unixTimestamp) {
        return Instant.ofEpochMilli(unixTimestamp).atZone(ZONE_ID_SH).toLocalDate();
    }

    /**
     * 级Unix时间戳转LocalDate
     *
     * @param second
     * @return
     */
    public static LocalDate secondUnix2LocalDate(Integer second) {
        return Instant.ofEpochSecond(second).atZone(ZONE_ID_SH).toLocalDate();
    }

    /**
     * 秒级Unix时间戳格式化为当前的0时0分0秒
     *
     * @param second
     * @return
     */
    public static Integer secondUnixFormatStartOfDay(Integer second) {
        LocalDate localDate = Instant.ofEpochSecond(second).atZone(ZONE_ID_SH).toLocalDate();
        long epochSecond = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().getEpochSecond();
        return Math.toIntExact(epochSecond);
    }

    /**
     * 秒级Unix时间戳转LocalDate
     *
     * @param unixTimestamp 毫秒级Unix时间戳
     * @return LocalDate
     */
    public static LocalDate secondUnix2LocalDate(Long unixTimestamp) {
        return Instant.ofEpochSecond(unixTimestamp).atZone(ZONE_ID_SH).toLocalDate();
    }

    public static String formatLocalDate(LocalDate date) {
        return date.format(com.douyu.wsd.hr.admin.human.client.util.time.LocalDateTimeUtils.DATE_FORMATTER);
    }

    /**
     * 获取当前LocalDate 0时0分0秒的毫秒级Unix时间戳
     *
     * @param localDate LocalDate
     * @return 毫秒级Unix时间戳
     */
    public static Long toEpochMilli(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取当前LocalDate 0时0分0秒的秒级Unix时间戳
     *
     * @param localDate
     * @return
     */
    public static Long toEpochSecond(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().getEpochSecond();
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
     * @param startDateInclusive 开始LocalDate。最好大于结束
     * @param endDateExclusive   结束LocalDate。最好小于开始
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
     * 获取当前时间是当前年度的上半年或者下半年
     *
     * @param localDate LocalDate
     * @return 2020_first_half_year=>2020年上半年；2025_second_half_year=>2025年下半年。
     */
    public static String getHalfYear(LocalDate localDate) {
        int year = localDate.getYear();
        int monthValue = localDate.getMonthValue();
        // 如果当前时间小于或者等于6月份，则是上半年，否则是下半年
        if (monthValue <= 6) {
            return year + "_first_half_year";
        } else {
            return year + "_second_half_year";
        }
    }

}
