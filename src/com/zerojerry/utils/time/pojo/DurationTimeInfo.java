package com.zerojerry.utils.time.pojo;

/**
 * @Description 获取相距时间。可以获取相差的天数、小时、分钟、毫秒、纳秒方法的返回值。
 * @Author ZeroJerry
 * @Date 2019/8/26 15:23
 * @Version 1.0
 */
public class DurationTimeInfo {
    /**
     * 开始时间距离结束时间的差值。
     */
    private Long durationTime;

    /**
     * 开始时间是否早于结束时间。true：是的。
     */
    private Boolean beforeWhether;

    public Long getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(Long durationTime) {
        this.durationTime = durationTime;
    }

    public Boolean getBeforeWhether() {
        return beforeWhether;
    }

    public void setBeforeWhether(Boolean beforeWhether) {
        this.beforeWhether = beforeWhether;
    }
}
