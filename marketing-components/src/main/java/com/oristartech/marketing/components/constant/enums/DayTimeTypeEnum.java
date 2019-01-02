package com.oristartech.marketing.components.constant.enums;

/**
 * @description: 全天或黄金场
 * @author: zhangdongdong
 * @createDate: 2018/11/13 1:45 PM
 * @Version: 1.0
 */
public enum DayTimeTypeEnum {
    ALL("ALL","全天"),
    PRIME("PRIME","黄金场");

    private String code;
    private String desc;

    DayTimeTypeEnum(String code, String desc) {
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
