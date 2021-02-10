package com.zerojerry.utils.time20210201;

import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

/**
 * @Description LocalDateTime的简单时间工具
 * @Date 2020/8/5 17:51
 * @Author 张俊力
 * @Version 1.0
 */
public class LocalDateTimeUtils {
    private LocalDateTimeUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static final ZoneId ZONE_ID_SH = ZoneId.of("Asia/Shanghai");

    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDD_CHINESE = "yyyy年MM月dd日";
    public static final String YYYYMMDD_SPACE_CHINESE = "yyyy 年 MM 月 dd 日";

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");
    public static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");
    public static final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");
    public static final DateTimeFormatter SHORT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmss");
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATETIME_CHINESIZE = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH点mm分");

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


    public static String getCurrentDateTimeStr(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
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
     * 毫秒级Unix时间戳转LocalDateTime
     *
     * @param unixTimestamp 毫秒级Unix时间戳
     * @return LocalDateTime
     */
    public static LocalDateTime unix2LocalDateTime(long unixTimestamp) {
        return Instant.ofEpochMilli(unixTimestamp).atZone(ZONE_ID_SH).toLocalDateTime();
    }


    public static String formatLocalDateTime(LocalDateTime datetime, String pattern) {
        return datetime.format(DateTimeFormatter.ofPattern(pattern));
    }


    public static LocalDateTime parseLocalDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER);
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
     * 获取当前时区的纪元毫秒值。
     *
     * @param dateTime
     * @return
     */
    public static Long toEpochMilli(LocalDateTime dateTime) {
        return dateTime.atZone(ZONE_ID_SH).toInstant().toEpochMilli();
    }

    //获取指定日期的秒
    public static Long getSecondsByTime(LocalDateTime time) {
        return time.atZone(ZONE_ID_SH).toInstant().getEpochSecond();
    }

    /**
     * 秒级Unix时间戳转换成ldt
     *
     * @param second 将秒级Unix时间戳
     * @return LocalDateTime
     */
    public static LocalDateTime getLocalDateTimeByUnix(Long second) {
        Instant instant = Instant.ofEpochSecond(second);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 获取秒级Unix时间戳
     *
     * @param ldt LocalDateTime
     * @return
     */
    public static Long getSecondUnixTime(LocalDateTime ldt) {
        // 转为东八区的 秒级Unix时间戳
        return ldt.toEpochSecond(ZoneOffset.ofHours(8));
    }

    /**
     * 获取系统时间零时零分零秒的“秒级Unix时间戳”
     *
     * @return 当天零时零分零秒 秒级Unix时间戳
     */
    public static Long getTodayStartUnixTime() {
        // 当天零时零分零秒
        LocalDateTime todayStartLdt = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        // 转为东八区的 秒级Unix时间戳
        return todayStartLdt.toEpochSecond(ZoneOffset.ofHours(8));
    }

    /**
     * 获取系统时间23时59分59秒的“秒级Unix时间戳”
     *
     * @return
     */
    public static Long getTodayEndUnixTime() {
        // 当天23时59分59秒
        LocalDateTime todayEndLdt = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        // 转为东八区的 秒级Unix时间戳
        return todayEndLdt.toEpochSecond(ZoneOffset.ofHours(8));
    }
}
