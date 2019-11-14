package com.zerojerry.utils.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Description 把Date转为JDK1.8的时间类的 工具类
 * @Author ZeroJerry66
 * @Date 2019/7/12 17:08
 * @Version 1.0
 */
public class TransformDateUtils {
    private TransformDateUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
