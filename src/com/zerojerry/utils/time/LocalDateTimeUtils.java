package com.zerojerry.utils.time;

import com.ceair.muservice.order.util.time.pojo.DurationTimeInfo;
import com.ceair.muservice.order.util.time.pojo.UnitOfTimeEnum;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;

/**
 * @Description LocalDateTime工具类。LocalDateTime包含年、月、日、时、分、秒。
 * @Author ZeroJerry66
 * @Date 2019/7/12 16:35
 * @Version 1.0
 */
public class LocalDateTimeUtils {
    private LocalDateTimeUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 获取相距时间。可以获取相差的天数、小时、分钟、毫秒、纳秒(如果开始时间晚于结束时间，那么是时间差的绝对值)。
     *
     * @param startDateTime  开始时间
     * @param endDateTime    结束时间
     * @param unitOfTimeEnum 时间单位枚举
     * @return
     */
    public static Long durationTime(LocalDateTime startDateTime, LocalDateTime endDateTime, UnitOfTimeEnum unitOfTimeEnum) {
        if (startDateTime == null || endDateTime == null || unitOfTimeEnum == null) {
            return null;
        }
        Duration duration = Duration.between(startDateTime, endDateTime);
        switch (unitOfTimeEnum) {
            // 相差的天数。
            case DAY:
                return duration.toDays();

            // 相差的小时数。
            case Hours:
                return duration.toHours();

            // 相差的分钟数
            case Minutes:
                return duration.toMinutes();

            // 相差的毫秒数
            case Millis:
                return duration.toMillis();

            // 相差的纳秒数
            case Nanos:
                return duration.toNanos();

            default:
                break;
        }
        return null;
    }

    /**
     * (推荐使用)获取相距时间。可以获取相差的天数、小时、分钟、毫秒、纳秒。开始时间必须早于结束时间,否则false。
     *
     * @param startDateTime  开始时间
     * @param endDateTime    结束时间
     * @param unitOfTimeEnum 时间单位枚举
     * @return DurationTimeInfo。
     */
    public static DurationTimeInfo durationTimePlus(LocalDateTime startDateTime, LocalDateTime endDateTime, UnitOfTimeEnum unitOfTimeEnum) {
        if (startDateTime == null || endDateTime == null || unitOfTimeEnum == null) {
            return null;
        }
        DurationTimeInfo durationTimeInfo = new DurationTimeInfo();
        Duration duration = Duration.between(startDateTime, endDateTime);

        // 如果isNegative()返回false，那么startDate早于endDate
        boolean negative = duration.isNegative();
        // 如果开始时间晚于结束时间(duration.isNegative()返回true的话，开始晚于结束)。直接返回false。
        if (duration.isNegative()) {
            durationTimeInfo.setBeforeWhether(false);
            return durationTimeInfo;
        } else {
            durationTimeInfo.setBeforeWhether(true);
        }

        switch (unitOfTimeEnum) {
            // 相差的天数。
            case DAY:
                durationTimeInfo.setDurationTime(duration.toDays());
                return durationTimeInfo;

            // 相差的小时数。
            case Hours:
                durationTimeInfo.setDurationTime(duration.toHours());
                return durationTimeInfo;

            // 相差的分钟数
            case Minutes:
                durationTimeInfo.setDurationTime(duration.toMinutes());
                return durationTimeInfo;

            // 相差的毫秒数
            case Millis:
                durationTimeInfo.setDurationTime(duration.toMillis());
                return durationTimeInfo;

            // 相差的纳秒数
            case Nanos:
                durationTimeInfo.setDurationTime(duration.toNanos());
                return durationTimeInfo;

            default:
                break;
        }
        return null;
    }

    /**
     * 传入其他时区和时间，获取本地时间。
     *
     * @param someWhereTimeZone 时区。+、-。
     * @param someWhereTime     时间。yyyy-MM-dd HH:mm:ss。
     * @return
     */
    public static LocalDateTime transformSomeWhereTime(String someWhereTimeZone, String someWhereTime) {
        // 传入的时区
        ZoneId timeZone = ZoneId.of(someWhereTimeZone);
        // 传入的时间字符串转为LocalDateTime
        LocalDateTime time = Jdk8TimeUtils.parseLocalDateTime(someWhereTime);
        // 传入的时间设置时区
        ZonedDateTime zonedDateTime = time.atZone(timeZone);
        // 传入的时间获取Instant
        Instant instant = zonedDateTime.toInstant();
        // 根据本地时区，获取本地时间
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * 获取当前日期是本年的第几周。
     * 默认星期一为一周的第一天。
     *
     * @param now LocalDateTime
     * @return 本年的第几周
     */
    public static Integer getWeekOfYear(LocalDateTime now) {
        // 初始化 默认星期一为一周的第一天
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);
        //使用DateTimeFormatter获取当前周数
        return now.get(weekFields.weekOfYear());
    }

    /**
     * 获取当前日期是星期几。Monday对应 1，Sunday对应 7...
     *
     * @param now LocalDateTime
     * @return
     */
    public static Integer getDayOfWeek(LocalDateTime now) {
        // 获取当前是星期几
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        // 因为DayOfWeek枚举是[MONDAY],[TUESDAY]的，所以用枚举反推出星期几的数字。
        for (int i = 1; i <= 7; i++) {
            if (DayOfWeek.of(i).equals(dayOfWeek)) {
                return i;
            }
        }
        return null;
    }

    /**
     * 时间取整。
     * （1）如果时间的分钟结尾是0或者5，则直接返回
     * （2）如果时间的分钟结尾大于0且小于5，那么去掉多余的这几分钟。例如：2019-10-30 9:24，则返回 2019-10-30 9:20
     * （3）如果时间的分钟结尾大于5且小于等于9，那么去掉多余的这几分钟。例如：2019-10-30 9:29，则返回 2019-10-30 9:25
     *
     * @param ldt
     * @return
     */
    public static LocalDateTime timeRounding(LocalDateTime ldt) {
        // 获取分钟数
        int minute = ldt.getMinute();
        if (minute % 5 == 0) {
            // 如果分钟数能被5整除，则直接返回
            return ldt;
        } else {
            // 分钟数 除以5 然后 乘以5，用来去掉多余的分钟数
            int roundingMinute = minute / 5 * 5;
            // 入参的ldt减去([入参的分钟数]减去[取整后的分钟数]的差值)，获取取整后的时间
            return ldt.minus(minute - roundingMinute, ChronoUnit.MINUTES);
        }
    }

}
