package com.oristartech.marketing.components.constant.enums;

/**
 * @description:
 * @author: zhangdongdong
 * @createDate: 2018/11/21 4:05 PM
 * @Version: 1.0
 */
public enum TimeRangeTypeEnum {
    DAYS("DAYS","天数"),
    DATE_RANGE("DATE_RANGE","时间段");

    private String code;
    private String desc;

    TimeRangeTypeEnum(String code, String desc) {
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
    }
}
