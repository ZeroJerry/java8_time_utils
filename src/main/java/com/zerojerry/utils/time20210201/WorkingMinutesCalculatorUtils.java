package com.zerojerry.utils.time20210201;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.LongStream;

/**
 * @Description 工作日工作分钟数计算。
 * @Date 2021/1/19 22:04
 * @Author 张俊力
 * @Version 1.0
 */
public class WorkingMinutesCalculatorUtils {
    private static final long MINUTES = 60;

    private static final long WORKING_MINUTES_PER_DAY = 24 * MINUTES;

    /**
     * 计算开始时间到结束时间的工作分钟数。跳过周六周日。
     *
     * @param startLdt 开始时间
     * @param endLdt   结束时间
     * @return 相差分钟数
     */
    public static Long getWorkingMinutes(final LocalDateTime startLdt, final LocalDateTime endLdt) {
        // 因为精确值到分钟，这里清空 秒数
        LocalDateTime start = LocalDateTime.of(startLdt.getYear(), startLdt.getMonth(), startLdt.getDayOfMonth(), startLdt.getHour(), startLdt.getMinute());
        LocalDateTime end = LocalDateTime.of(endLdt.getYear(), endLdt.getMonth(), endLdt.getDayOfMonth(), endLdt.getHour(), endLdt.getMinute());
        LocalDate startLd = start.toLocalDate();
        LocalDate endLd = end.toLocalDate();

        // 开始ld 到 结束ld的天数，因为开始那一天也算一天，所以最后 +1
        long allDaysBetween = ChronoUnit.DAYS.between(startLd, endLd) + 1;
        // 遍历从startLocalDateTime到结束日期
        long allWorkingMinute = LongStream.range(0, allDaysBetween)
                // 保留是工作日的日期
                .filter(i -> isWorkingDay(start.plusDays(i)))
                // 工作天数 * 每天工作的分钟数
                .count() * WORKING_MINUTES_PER_DAY;

        // start - working_day_from_start。开始时间的冗余时间
        long tailRedundantMinute = 0;
        if (isWorkingDay(start)) {
            // 如果开始ldt是工作日，那么计算：开始ldt - 当天0时0分
            tailRedundantMinute = Duration.between(startLd.atTime(0, 0), start).toMinutes();
        }

        // working_day_end - end。结束时间的冗余时间
        long headRedundantMinute = 0;
        if (isWorkingDay(end)) {
            // 如果结束ldt是工作日，那么计算：结束ldt - 下一天的0时0分
            headRedundantMinute = Duration.between(end, endLd.plusDays(1).atStartOfDay()).toMinutes();
        }
        // 开始到结束的工作分钟数- 开始时间的冗余时间 - 结束时间的冗余时间
        return allWorkingMinute - tailRedundantMinute - headRedundantMinute;
    }

    /**
     * 判断是否工作日
     *
     * @param time LocalDateTime
     * @return true：是的
     */
    private static boolean isWorkingDay(final LocalDateTime time) {
        return time.getDayOfWeek().getValue() < DayOfWeek.SATURDAY.getValue();
    }
}
