package com.oristartech.marketing.components.constant.enums;

/**
 * @Auther: hexu
 * @Date: 2018/11/27 16:33
 * @Description:
 */
public enum DistributionMapEnum {

    SHOW_TYPE("SHOW_TYPE","场次类型"),
    MVOIE_NATURE("MVOIE_NATURE","影片属性"),
    MVOIE_TYPE("MVOIE_TYPE","影片类型"),
    MOVIE_STANDARD("MOVIE_STANDARD","影片制式");
    private String code;
    private String desc;

    DistributionMapEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

}
