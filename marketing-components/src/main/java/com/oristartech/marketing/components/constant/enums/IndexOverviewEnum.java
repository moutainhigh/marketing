package com.oristartech.marketing.components.constant.enums;

/**
 * com.tthappy.m.core.constant.enums
 *
 * @author fanzixian
 * @createDate 2018-11-26
 */
public enum IndexOverviewEnum {
    MONTH_OFFICE_BOX("month_office_box", "当月票房(万)"),
    MONTH_AUDIENCE_COUNT("month_audience_count", "当月人次(万)"),
    MONTH_SHOW_COUNT("month_show_count", "当月场次"),
    MONTH_AVG_DAY_HALL_BOX_OFFICE("month_avg_day_hall_box_office", "当月单日单厅收益"),
    MONTH_AVG_DAY_SEAT_BOX_OFFICE("month_avg_day_seat_box_office", "当月单日单座收益"),
    MONTH_AVG_SEAT_VIEW_RATE("month_avg_seat_view_rate", "当月上座率"),
    BOX_OFFICE_TOTAL("box_office_total", "累计票房(万)"),
    SHOW_COUNT_TOTAL("show_count_total", "累计场次"),
    AUDIENCE_COUNT_TOTAL("audience_count_total", "累计人次(万)"),
    AVG_PRICE("avg_price", "平均票价"),
    AVG_SEAT_VIEW_RATE("avg_seat_view_rate", "上座率"),
    SHOW_DAYS("show_days", "上映天数");
    private String code;
    private String desc;

    IndexOverviewEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }}
