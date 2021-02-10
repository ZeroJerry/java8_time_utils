package main.java.com.zerojerry.utils.time20210201;

import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.stream.IntStream;

/**
 * @Description 在Java中仅计算两个日期之间的工作时间（不包括周末）。参考：https://www.it1352.com/2037884.html
 * @Date 2021/1/19 22:04
 * @Author 张俊力
 * @Version 1.0
 */
public class WorkingMinutesCalculator {
    private static final int WORK_HOUR_START = 9;
    private static final int WORK_HOUR_END = 17;
    private static final long MINUTES = 60;

    private static final long WORKING_HOURS_PER_DAY = WORK_HOUR_END - WORK_HOUR_START;
    private static final long WORKING_MINUTES_PER_DAY = WORKING_HOURS_PER_DAY * MINUTES;

    public int getWorkingMinutesSince(final Timestamp startTime) {
        Timestamp now = Timestamp.from(Instant.now());
        return getWorkingMinutes(startTime, now);
    }

    public int getWorkingMinutes(final Timestamp startTime, final Timestamp endTime) {
        if (null == startTime || null == endTime) {
            throw new IllegalStateException();
        }
        if (endTime.before(startTime)) {
            return 0;
        }

        // 开始、结束时间LocalDateTime
        LocalDateTime from = startTime.toLocalDateTime();
        LocalDateTime to = endTime.toLocalDateTime();
        // 开始、结束时间LocalDate
        LocalDate fromDay = from.toLocalDate();
        LocalDate toDay = to.toLocalDate();

        // 开始结束相差的天数
        int allDaysBetween = (int) (ChronoUnit.DAYS.between(fromDay, toDay) + 1);

        long allWorkingMinutes = IntStream.range(0, allDaysBetween)
                .filter(i -> isWorkingDay(from.plusDays(i)))
                .count() * WORKING_MINUTES_PER_DAY;

        // from - working_day_from_start
        long tailRedundantMinutes = 0;
        if (isWorkingDay(from)) {
            if (isWorkingHours(from)) {
                tailRedundantMinutes = Duration.between(fromDay.atTime(WORK_HOUR_START, 0), from).toMinutes();
            } else if (from.getHour() > WORK_HOUR_START) {
                tailRedundantMinutes = WORKING_MINUTES_PER_DAY;
            }
        }

        // working_day_end - to
        long headRedundanMinutes = 0;
        if (isWorkingDay(to)) {
            if (isWorkingHours(to)) {
                headRedundanMinutes = Duration.between(to, toDay.atTime(WORK_HOUR_END, 0)).toMinutes();
            } else if (from.getHour() < WORK_HOUR_START) {
                headRedundanMinutes = WORKING_MINUTES_PER_DAY;
            }
        }
        return (int) (allWorkingMinutes - tailRedundantMinutes - headRedundanMinutes);
    }

    /**
     * 判断是否工作日
     *
     * @param time LocalDateTime
     * @return true：是的
     */
    private boolean isWorkingDay(final LocalDateTime time) {
        return time.getDayOfWeek().getValue() < DayOfWeek.SATURDAY.getValue();
    }

    /**
     *
     * @param time LocalDateTime
     * @return
     */
    private boolean isWorkingHours(final LocalDateTime time) {
        int hour = time.getHour();
        return WORK_HOUR_START <= hour && hour <= WORK_HOUR_END;
    }
}
