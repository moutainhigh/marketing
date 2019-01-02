package com.oristartech.marketing.components.constant.enums;

/**
 * com.tthappy.m.core.constant.enums
 * fanzixian
 * 2018/11/15
 */
public enum SearchCompanyEnum {

    CINEMA_CHAIN("CINEMA_CHAIN", "院线"),
    MOVIE_MANAGE("MOVIE_MANAGE", "影投"),
    MERCHANT("MERCHANT", "商户");
    private String code;
    private String desc;

    SearchCompanyEnum(String code, String desc) {
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
