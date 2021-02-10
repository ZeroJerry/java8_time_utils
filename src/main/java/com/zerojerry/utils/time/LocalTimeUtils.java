package com.zerojerry.utils.time;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description LocalTime的简单时间工具
 * @Date 2020/8/5 19:12
 * @Author ZeroJerry
 * @Version 1.0
 */
public class LocalTimeUtils {
    private LocalTimeUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * HHmmss
     *
     * @return
     */
    public static String getCurrentTimeStr() {
        return LocalTime.now().format(LocalDateTimeUtils.TIME_FORMATTER);
    }

    public static String getCurrentTimeStr(String pattern) {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
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

    public static String formatLocalTime(LocalTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 字符串转时间（LocalTime）
     *
     * @param timeStr HHmmss
     * @return
     */
    public static LocalTime parseLocalTime(String timeStr) {
        return LocalTime.parse(timeStr, LocalDateTimeUtils.TIME_FORMATTER);
    }

    public static String formatLocalTime(LocalTime time) {
        return time.format(LocalDateTimeUtils.TIME_FORMATTER);
    }
}
