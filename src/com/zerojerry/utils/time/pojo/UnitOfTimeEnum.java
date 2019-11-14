package com.zerojerry.utils.time.pojo;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description 时间单位枚举
 * @Author ZeroJerry66
 * @Date 2019/7/12 16:48
 * @Version 1.0
 */
public enum UnitOfTimeEnum {
    // 天
    DAY,

    // 小时
    Hours,

    // 分钟
    Minutes,

    // 毫秒
    Millis,

    // 纳秒
    Nanos;

    private String Code;

    public String getCode() {
        return Code;
    }

    /**
     * 通过枚举code字符串获得枚举。
     *
     * @param code 简码
     * @return 枚举
     */
    public static UnitOfTimeEnum toEnum(String code) {
        for (UnitOfTimeEnum status : values()) {
            if (StringUtils.equalsIgnoreCase(status.getCode(), code)) {
                return status;
            }
        }
        return null;
    }

}
