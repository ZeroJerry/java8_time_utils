package com.zerojerry.utils.time20210201;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.douyu.wsd.hr.admin.human.client.util.time.LocalDateTimeUtils.ZONE_ID_SH;

@Slf4j
public class TimeUtils {

    public static BigDecimal daySeconds = new BigDecimal(24 * 3600);

    public static BigDecimal yearSeconds = new BigDecimal(365 * 24 * 3600);

    public static int getCurrUnixTime() {
        Date date = new Date();
        return (int) (date.getTime() / 1000);
    }

    public static Long getCurrUnixTimeLong() {
        Date date = new Date();
        return (date.getTime() / 1000);
    }

    /**
     * 通过时间秒数 获得今天日期
     *
     * @param timeSecond the time second
     * @return the data string
     */
    public static String getDataString(Integer timeSecond) {
        Instant instant = Instant.ofEpochSecond(timeSecond);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime time = LocalDateTime.ofInstant(instant, zone);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return time.format(formatter);
    }

    /**
     * 通过时间秒数 获得按照 “yyyy-MM-dd HH:mm:ss” 格式展示的日期
     *
     * @param second 秒
     * @return 日期字符串
     */
    public static String second2DateString(Integer second) {
        LocalDateTime time = getLocalDateTimeFromSecond(second);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return time.format(formatter);
    }

    public static String second2YearMonthStr(Integer second) {
        LocalDateTime time = getLocalDateTimeFromSecond(second);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        return time.format(formatter);
    }

    /**
     * 验证输入的两个单位为秒的时间，是否有跨天
     *
     * @param startTime 开始时间，单位秒
     * @param endTime   结束时间，单位秒
     * @return true 或者 false
     */
    public static boolean validateDateRange(Integer startTime, Integer endTime) {
        LocalDateTime startLocalDateTime = getLocalDateTimeFromSecond(startTime);
        LocalDateTime endLocalDateTime = getLocalDateTimeFromSecond(endTime);
        if (startLocalDateTime.getYear() != endLocalDateTime.getYear()) {
            return false;
        }
        if (startLocalDateTime.getMonth() != endLocalDateTime.getMonth()) {
            return false;
        }
        int start = startLocalDateTime.getDayOfMonth();
        int end = endLocalDateTime.getDayOfMonth();
        return start == end;
    }

    /**
     * 将秒级时间戳转换成日期
     */
    public static LocalDateTime getLocalDateTimeFromSecond(Integer second) {
        Instant instant = Instant.ofEpochSecond(second);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 将LocalDateTime转换成秒
     */
    public static Integer getSecondFromLocalDateTime(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZONE_ID_SH).toInstant();
        Long result = instant.toEpochMilli() / 1000;
        return result.intValue();
    }

    /**
     * 获取当前时间所在天的开始和结束时间
     */
    public static int[] getDayStartEndUnixTime(Integer seconds) {
        int[] dayStartEnd = new int[2];

        Instant instant = Instant.ofEpochSecond(seconds);
        LocalDateTime time = LocalDateTime.ofInstant(instant, ZONE_ID_SH);
        LocalDate nowDate = time.toLocalDate();

        //设置零点
        LocalDateTime beginTime = LocalDateTime.of(nowDate, LocalTime.MIN);
        Instant instantBeginTime = beginTime.atZone(ZONE_ID_SH).toInstant();
        dayStartEnd[0] = (int) instantBeginTime.getEpochSecond();

        LocalDateTime endTime = LocalDateTime.of(nowDate, LocalTime.MAX);
        Instant instantEndTime = endTime.atZone(ZONE_ID_SH).toInstant();
        dayStartEnd[1] = (int) instantEndTime.getEpochSecond();

        return dayStartEnd;
    }

    /**
     * 将日期字符解析为秒
     */
    public static Integer parseDateString(String dateString, String formatString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatString).withZone(ZoneId.systemDefault());
        try {
            LocalDate localDate = LocalDate.parse(dateString, formatter);
            return localDate2Second(localDate);
        } catch (Exception e) {
            log.error("日期字符串解析异常,待解析字符串：{}, 解析格式：{}", dateString, formatString);
            return null;
        }
    }

    /**
     * 计算当前天所在月份的第一天的开始秒数
     */
    public static Integer getFirstDaySecondOfMonth(LocalDate calcDate) {
        Integer calcSecond = localDate2Second(calcDate);
        int[] curStartAndEndTime = getMonthStartEndUnixTime(calcSecond);
        return curStartAndEndTime[0];
    }

    /**
     * 将localdate转换成秒
     */
    public static Integer localDate2Second(LocalDate localDate) {
        LocalDateTime localDateTime = LocalDateTime
                .of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth(), 0, 0, 0);
        return getSecondFromLocalDateTime(localDateTime);
    }

    /**
     * 秒转换成日期
     */
    public static LocalDate second2LocalDate(Integer second) {
        return getLocalDateTimeFromSecond(second).toLocalDate();
    }

    /**
     * 获取当前时间所在月的开始和结束时间
     */
    public static int[] getMonthStartEndUnixTime(Integer seconds) {
        int[] dayStartEnd = new int[2];

        Instant instant = Instant.ofEpochSecond(seconds);
        LocalDateTime time = LocalDateTime.ofInstant(instant, ZONE_ID_SH);
        LocalDate nowDate = time.toLocalDate();

        //设置零点
        LocalDateTime beginTime = LocalDateTime.of(nowDate, LocalTime.MIN);
        beginTime = beginTime.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
        Instant instantBeginTime = beginTime.atZone(ZONE_ID_SH).toInstant();
        dayStartEnd[0] = (int) instantBeginTime.getEpochSecond();

        LocalDateTime endTime = LocalDateTime.of(nowDate, LocalTime.MAX);
        endTime = endTime.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
        Instant instantEndTime = endTime.atZone(ZONE_ID_SH).toInstant();
        dayStartEnd[1] = (int) instantEndTime.getEpochSecond();

        return dayStartEnd;
    }

    public static Integer setHMM20(Integer time) {
        Date date = new Date(time.longValue() * 1000);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Long timeLong = calendar.getTime().getTime() / 1000;
        return timeLong.intValue();
    }

    /**
     * DayOfWeek枚举转为中文字符串
     *
     * @param dayOfWeek LocalDate.getDayOfWeek()获取的星期几的 枚举
     * @return
     */
    public static String dayOfWeek2Str(DayOfWeek dayOfWeek) {
        if (dayOfWeek == null) {
            return StringUtils.EMPTY;
        }
        //获取考勤日期是周几
        switch (dayOfWeek) {
            case MONDAY:
                return "星期一";
            case TUESDAY:
                return "星期二";
            case WEDNESDAY:
                return "星期三";
            case THURSDAY:
                return "星期四";
            case FRIDAY:
                return "星期五";
            case SATURDAY:
                return "星期六";
            case SUNDAY:
                return "星期天";
            default:
                return StringUtils.EMPTY;
        }
    }

}
