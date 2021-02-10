package com.zerojerry.utils.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static com.zerojerry.utils.time.LocalDateTimeUtils.ZONE_ID_SH;


/**
 * @Description 把Date转为JDK1.8的时间类的 工具类
 * @Author ZeroJerry
 * @Date 2019/7/12 17:08
 * @Version 1.0
 */
public class DateUtils {
    private DateUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Date转为LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime convertDate2Ldt(Date date) {
        Instant instant = date.toInstant();
        // 获取系统默认时区。
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param localDateTime
     * @return
     */
    public static Date convertLdt2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZONE_ID_SH).toInstant());
    }

    /**
     * java.util.Date转秒级Unix时间戳
     *
     * @return
     */
    public static Long date2Second(Date date) {
        return date.getTime() / 1000;
    }

}
